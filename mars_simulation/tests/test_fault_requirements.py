"""What fault detection still has to do. Each xfail test is a fault we can already inject but
don't yet catch or survive.

Every test is marked xfail(strict=True): `pytest` stays green and lists them as "xfailed".
When a fix makes one pass, pytest reports it as a failure (XPASS strict) so the marker gets
removed. raises=AssertionError means a crash doesn't count as an expected failure.

How a detection is read: a detector adds lists of labels to obs under any key ending in
"_faults" (e.g. temp_faults=["stuck"]). Each flag is seen as "key:label", e.g.
"actuator_faults:heater_burnout". A test needs a flag naming the broken part (the substring in
must_flag) while the fault is active, within one sol, nothing flagged before the fault, and for
actuator and plant faults, the healthy sensor not blamed (must_stay_clear).

New detectors plug in through detection_layers() below. All runs are HybridPlant(FakePlant)
with the irrigation, HVAC and CO2/lamp controllers; the fault starts at tick 300 so the crop is
using water and the recycler is running.
"""
from __future__ import annotations

from typing import Any, Dict, List, Optional, Sequence

import pytest

from actuators import AtmosphereActuatorLayer
from controllers import HVACTemperatureController, IrrigationController
from estimators import AtmosphereTemperatureMonitor, MonitorInLoop
from interface import FakePlant, Fault, TickLogger, run
from interface import bindings
from interface.plant import HybridPlant
from sensors import AtmosphereSensorLayer, chain_sensor_layers

START, LENGTH, N_TICKS = 300, 72, 400
SOL = 25  # ticks a detection may take
SAFE_MIN_C, SAFE_MAX_C = 7.0, 32.0  # edge of the CRITICAL band in interface.sensors.temp_alarm_band


def detection_layers():
    """(sensor_layer, supervisor) for run(). Add new monitors here."""
    loop = MonitorInLoop(AtmosphereTemperatureMonitor(temp_sensor_sigma_C=0.15, temp_model_sigma_C=0.25,
                                                      temp_shift_C=1.0, temp_h_sigmas=4.0))
    return chain_sensor_layers(AtmosphereSensorLayer(seed=42), loop.sensor_layer), loop.supervisor


def hold_co2(obs: Dict[str, Any]) -> Dict[str, float]:
    co2 = obs.get("gh.co2_ppm")
    mol = 0.0 if co2 is None else (1200.0 - co2) * 1e-6 * 2050.0
    return {"cmd.crop_power_W": 200.0, "cmd.co2_inject_mol": max(0.0, min(5.0, mol))}


def run_with(faults: Sequence[Fault]) -> List[Dict[str, Any]]:
    sensor_layer, supervisor = detection_layers()
    log = TickLogger()
    run(HybridPlant(plant=FakePlant(), sync_biosim_temperature=False),
        {"irrigation": IrrigationController(), "hvac": HVACTemperatureController(), "atmo": hold_co2},
        N_TICKS, sensor_layer=sensor_layer, supervisor=supervisor,
        actuator_layer=AtmosphereActuatorLayer(), fault_schedule=faults, logger=log)
    return log.records


def flags(record: Dict[str, Any]) -> List[str]:
    return [f"{key}:{label}" for key, labels in record["obs"].items()
            if key.endswith("_faults") and isinstance(labels, list) for label in labels]


def assert_detected(faults: Sequence[Fault], must_flag: str, must_stay_clear: Sequence[str] = ()) -> None:
    records = run_with(faults)
    before = [(r["tick"], flags(r)) for r in records if r["tick"] < START and flags(r)]
    assert not before, f"false alarms before the fault: {before[:3]}"
    window = [r for r in records if START <= r["tick"] < START + min(LENGTH, SOL)]
    hit = next((r for r in window if any(must_flag in f for f in flags(r))), None)
    seen = sorted({f for r in window for f in flags(r)})
    assert hit is not None, f"nothing flagged {must_flag!r} within {SOL} ticks; flagged instead: {seen}"
    blamed = sorted({f for r in window for f in flags(r) for k in must_stay_clear if f.startswith(k + ":")})
    assert not blamed, f"blamed a healthy part: {blamed}"


def sensor_fault(kind: str, target: str, **params) -> List[Fault]:
    return [Fault(tick=START, kind=kind, target=target, params=params, duration=LENGTH)]


def malfunction(module: str, intensity: str = "SEVERE_MALF") -> List[Fault]:
    return [Fault(tick=START, kind="malfunction", target=module,
                  params={"intensity": intensity, "length": "PERMANENT_MALF"}, duration=LENGTH)]


xfail = pytest.mark.xfail(strict=True, raises=AssertionError)


# Controls: detections that already work, so a broken harness can't hide behind the xfails.

def test_control_temperature_sensor_bias_is_detected():
    assert_detected(sensor_fault("bias", "gh.temp_C", offset=3.5), must_flag="temp_faults:")


def test_control_heater_burnout_is_detected():
    assert_detected(sensor_fault("stuck", "cmd.heater_W", value=0.0), must_flag="heater",
                    must_stay_clear=["temp_faults"])


# Sensors with fault injection but no detector

@xfail
def test_o2_sensor_bias_is_detected():
    # reads 19 % O2 when the air is at 21 %
    assert_detected(sensor_fault("bias", "gh.o2_frac", offset=-0.02), must_flag="o2")


@xfail
def test_o2_sensor_stuck_is_detected():
    assert_detected(sensor_fault("stuck", "gh.o2_frac"), must_flag="o2")


@xfail
def test_pressure_sensor_bias_is_detected():
    # the CO2 model scales total gas by measured pressure, so today this blames the CO2 sensor
    assert_detected(sensor_fault("bias", "gh.pressure_kPa", offset=5.0), must_flag="pressure",
                    must_stay_clear=["co2_faults"])


@xfail
def test_pressure_sensor_dropout_is_detected():
    assert_detected(sensor_fault("dropout", "gh.pressure_kPa"), must_flag="pressure")


# Actuator faults the monitor misses or blames on a sensor

@xfail
def test_heater_at_half_output_is_detected():
    # burnout detection only fires when the draw is under 10 W
    assert_detected(sensor_fault("efficiency", "cmd.heater_W", factor=0.5), must_flag="heater",
                    must_stay_clear=["temp_faults"])


@xfail
def test_co2_injector_stuck_open_is_detected():
    # 0.5 mol/tick is ~240 ppm/tick into 2050 mol of air; only "blocked" is checked today
    assert_detected(sensor_fault("stuck", "cmd.co2_inject_mol", value=0.5), must_flag="injector",
                    must_stay_clear=["co2_faults"])


# BioSim malfunctions with no Python-side detection

@xfail
def test_dust_storm_on_solar_array_is_detected():
    assert_detected(malfunction(bindings.SOLAR), must_flag="solar")


@xfail
def test_water_recycler_failure_is_detected():
    # in BioSim this one is permanent: clearing the malfunction never restores the recycler
    assert_detected(malfunction(bindings.RECYCLER), must_flag="recycler")


# Surviving a fault, not just detecting it

@xfail
def test_cabin_stays_safe_through_heater_burnout():
    # burnout is caught within an hour, but nothing replaces the lost heat and the cabin falls to
    # about -15 °C. Lamp power turns into heat (90 % in the thermal model), so a supervisor that
    # raises cmd.crop_power_W on burnout is one way to pass this
    records = run_with(sensor_fault("stuck", "cmd.heater_W", value=0.0))
    temps = [r["truth"]["gh.temp_truth_C"] for r in records]
    assert SAFE_MIN_C <= min(temps) and max(temps) <= SAFE_MAX_C, \
        f"cabin went {min(temps):.1f} to {max(temps):.1f} °C; safe is {SAFE_MIN_C:g} to {SAFE_MAX_C:g} °C"

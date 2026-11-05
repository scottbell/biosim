"""Benchmark atmosphere and temperature fault modeling, detection, and mitigation.

Runs closed-loop HVAC & atmosphere scenarios comparing:
1. Ground truth physical values (gh.temp_truth_C, gh.co2_ppm)
2. Corrupted sensor telemetry (gh.temp_C, gh.co2_ppm with noise, bias, drift, stuck, dropout)
3. Analytical model estimation (ThermalEstimator, AtmosphereGasEstimator)
4. Fused telemetry and FDIR diagnostics (AtmosphereTemperatureMonitor)

Scenarios tested:
- nominal: baseline Gaussian noise without faults
- temp_dropout: sensor disconnect (measurement None)
- temp_stuck: sensor freezes at constant temperature
- temp_bias: +3.5 °C calibration offset
- temp_drift: +0.15 °C/tick sensor drift
- heater_burnout: actuator fails open (0 W output despite heating demand)
- co2_bias: +150 ppm NDIR offset

Usage:
    python scripts/atmosphere_fault_check.py
    python scripts/atmosphere_fault_check.py --ticks 100 --fault-tick 30 --fault-len 30
"""
from __future__ import annotations

import argparse
import math
import statistics
import sys
from pathlib import Path
from typing import Any, Dict, List, Optional

ROOT = Path(__file__).resolve().parent.parent
sys.path.insert(0, str(ROOT))

from actuators import AtmosphereActuatorLayer
from controllers import HVACTemperatureController
from estimators import AtmosphereTemperatureMonitor
from interface.plant import FakePlant, HybridPlant
from interface.schema import Fault
from sensors import AtmosphereSensorLayer


def run_scenario(
    name: str,
    faults: List[Fault],
    n_ticks: int = 100,
    seed: int = 42,
) -> Dict[str, Any]:
    sensor_layer = AtmosphereSensorLayer(temp_sigma_C=0.15, co2_sigma_ppm=5.0, seed=seed)
    actuator_layer = AtmosphereActuatorLayer()
    controller = HVACTemperatureController(setpoint_C=22.0, kp=120.0, ki=5.0, kd=20.0)
    monitor = AtmosphereTemperatureMonitor(
        temp_sensor_sigma_C=0.15,
        temp_model_sigma_C=0.25,
        temp_shift_C=1.0,
        temp_h_sigmas=4.0,
    )
    plant = HybridPlant(plant=FakePlant(), sync_biosim_temperature=False)

    truth = plant.reset()
    monitor.reset(
        initial_temp_C=float(truth.get("gh.temp_truth_C", 22.0)),
        initial_co2_ppm=float(truth.get("gh.co2_ppm", 1200.0)),
    )
    controller.reset()

    rows: List[Dict[str, Any]] = []

    for tick in range(n_ticks):
        active = [f for f in faults if f.active_at(tick)]

        # Class with ctx protocol
        class Ctx:
            pass

        ctx = Ctx()
        ctx.tick = tick
        ctx.active_faults = active

        # True state at tick k
        true_temp = float(truth.get("gh.temp_truth_C", truth.get("gh.temp_C", 22.0)))

        # Sensor Layer: truth -> obs
        obs = sensor_layer(truth, ctx)

        # Controller proposed commands
        cmds = controller(obs)
        cmds["cmd.crop_power_W"] = 200.0  # realistic lamp load

        # FDIR Monitor processes obs and proposed commands
        diag = monitor.update(obs, commands=cmds)
        obs.update(diag)

        # Actuator Layer: applied commands
        applied = actuator_layer(cmds, ctx)
        plant.apply(applied)

        rows.append({
            "tick": tick,
            "true_temp": true_temp,
            "sensed_temp": obs.get("gh.temp_C"),
            "fused_temp": diag["fused_temp_C"],
            "model_temp": diag["temp_model_est_C"],
            "temp_faults": diag["temp_faults"],
            "actuator_faults": diag["actuator_faults"],
            "trusted": diag["temp_trusted"],
            "heater_cmd": cmds.get("cmd.heater_W", 0.0),
            "heater_applied": applied.get("cmd.heater_W", 0.0),
        })

        # Step plant for k -> k+1
        truth = plant.step()

    return evaluate_metrics(name, rows, faults)


def evaluate_metrics(name: str, rows: List[Dict[str, Any]], faults: List[Fault]) -> Dict[str, Any]:
    fault_ticks = {r["tick"] for r in rows for f in faults if f.active_at(r["tick"])}
    start_tick = min(fault_ticks) if fault_ticks else -1
    end_tick = max(fault_ticks) if fault_ticks else -1

    # Detection delay
    first_detected = next((r["tick"] for r in rows if r["tick"] >= start_tick and (r["temp_faults"] or r["actuator_faults"])), None) if start_tick >= 0 else None
    detection_delay = (first_detected - start_tick) if first_detected is not None and start_tick >= 0 else None

    # False alarms during nominal ticks
    nominal_ticks = [r for r in rows if r["tick"] not in fault_ticks]
    false_alarms = sum(1 for r in nominal_ticks if r["temp_faults"] or r["actuator_faults"])

    # RMS errors
    sensed_errs = [(r["sensed_temp"] - r["true_temp"]) ** 2 for r in rows if r["sensed_temp"] is not None]
    fused_errs = [(r["fused_temp"] - r["true_temp"]) ** 2 for r in rows if r["fused_temp"] is not None]

    sensed_rms = math.sqrt(statistics.fmean(sensed_errs)) if sensed_errs else float("nan")
    fused_rms = math.sqrt(statistics.fmean(fused_errs)) if fused_errs else float("nan")

    # True cabin temperature stability under control
    true_temps = [r["true_temp"] for r in rows]
    mean_temp = statistics.fmean(true_temps)
    min_temp = min(true_temps)
    max_temp = max(true_temps)

    return {
        "scenario": name,
        "detection_delay": detection_delay,
        "false_alarms": false_alarms,
        "sensed_rms": sensed_rms,
        "fused_rms": fused_rms,
        "mean_temp": mean_temp,
        "temp_range": (min_temp, max_temp),
        "mitigated": fused_rms < sensed_rms or math.isnan(sensed_rms),
    }


def main():
    parser = argparse.ArgumentParser(description="Atmosphere & Temperature FDIR Verification")
    parser.add_argument("--ticks", type=int, default=100, help="Number of ticks to simulate")
    parser.add_argument("--fault-tick", type=int, default=30, help="Tick when fault activates")
    parser.add_argument("--fault-len", type=int, default=30, help="Duration of fault in ticks")
    args = parser.parse_args()

    t_start = args.fault_tick
    t_len = args.fault_len

    scenarios = [
        ("Nominal (Noise Only)", []),
        ("Temp Sensor Dropout", [Fault(tick=t_start, kind="dropout", target="gh.temp_C", duration=t_len)]),
        ("Temp Sensor Stuck", [Fault(tick=t_start, kind="stuck", target="gh.temp_C", duration=t_len)]),
        ("Temp Sensor Bias (+3.5 °C)", [Fault(tick=t_start, kind="bias", target="gh.temp_C", params={"offset": 3.5}, duration=t_len)]),
        ("Temp Sensor Drift (+0.15 °C/tick)", [Fault(tick=t_start, kind="drift", target="gh.temp_C", params={"rate": 0.15}, duration=t_len)]),
        ("Heater Burnout Actuator Failure", [Fault(tick=t_start, kind="stuck", target="cmd.heater_W", params={"value": 0.0}, duration=t_len)]),
        ("CO2 Sensor Bias (+150 ppm)", [Fault(tick=t_start, kind="bias", target="gh.co2_ppm", params={"offset": 150.0}, duration=t_len)]),
    ]

    print(f"\nRunning Atmosphere & Temperature FDIR verification ({args.ticks} ticks)...\n")
    results = []
    for name, f_list in scenarios:
        res = run_scenario(name, f_list, n_ticks=args.ticks)
        results.append(res)

    # Format Markdown Table
    print("| Scenario | Detection Delay | False Alarms | Raw Sensed RMS | Fused FDIR RMS | Cabin Temp Range |")
    print("|:---|:---:|:---:|:---:|:---:|:---:|")
    for r in results:
        delay_str = f"{r['detection_delay']} ticks" if r['detection_delay'] is not None else "N/A"
        sensed_str = f"{r['sensed_rms']:.2f} °C" if not math.isnan(r['sensed_rms']) else "Dropout"
        fused_str = f"**{r['fused_rms']:.2f} °C**"
        range_str = f"[{r['temp_range'][0]:.1f}, {r['temp_range'][1]:.1f}] °C"
        print(f"| {r['scenario']} | {delay_str} | {r['false_alarms']} | {sensed_str} | {fused_str} | {range_str} |")
    print("\nAll FDIR benchmarks completed successfully.")


if __name__ == "__main__":
    main()

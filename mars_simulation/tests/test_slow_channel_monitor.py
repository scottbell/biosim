"""SlowChannelMonitor (O2 and pressure sensors) and reduced heater output, at unit level."""
from __future__ import annotations

import random

from estimators import AtmosphereTemperatureMonitor
from estimators.thermal import SlowChannelMonitor


def pressure_channel() -> SlowChannelMonitor:
    return SlowChannelMonitor(sensor_sigma=0.1, shift=2.0, h=1.5)


def noisy(value: float, n: int, sigma: float = 0.1, seed: int = 0):
    rng = random.Random(seed)
    return [value + rng.gauss(0.0, sigma) for _ in range(n)]


def test_healthy_noisy_sensor_is_never_flagged():
    ch = pressure_channel()
    for v in noisy(101.0, 2000, seed=1):
        faults, residual, healthy = ch.update(v)
        assert faults == [] and healthy and abs(residual) < 1.0


def test_step_bias_is_flagged_at_once_and_reference_is_held():
    ch = pressure_channel()
    for v in noisy(101.0, 50):
        ch.update(v)
    for v in noisy(106.0, 30, seed=2):
        faults, _, healthy = ch.update(v)
        assert faults == ["high_bias"] and not healthy
    assert abs(ch.reference - 101.0) < 0.3


def test_small_bias_is_not_absorbed_by_the_reference():
    ch = pressure_channel()
    for v in noisy(101.0, 50):
        ch.update(v)
    seen = [ch.update(v)[0] for v in noisy(99.5, 10, seed=3)]
    assert ["low_bias"] in seen


def test_alarm_clears_after_the_bias_goes_away():
    ch = pressure_channel()
    for v in noisy(101.0, 20) + noisy(106.0, 10, seed=2):
        ch.update(v)
    results = [ch.update(v)[0] for v in noisy(101.0, 20, seed=4)]
    assert results[-1] == [] and results[0] == ["high_bias"]


def test_slow_real_change_is_followed_not_flagged():
    ch = pressure_channel()
    rng = random.Random(5)
    for i in range(300):
        faults, _, _ = ch.update(101.0 + 0.1 * i + rng.gauss(0.0, 0.1))  # 30 kPa over 300 ticks
        assert faults == []


def test_dropout_and_stuck():
    ch = pressure_channel()
    for v in noisy(101.0, 10):
        ch.update(v)
    assert ch.update(None) == (["dropout"], None, False)
    ch.update(101.05)
    assert ch.update(101.05)[0] == []
    assert ch.update(101.05)[0] == ["stuck"]
    assert ch.update(101.2)[0] == []


def test_noise_free_constant_signal_is_not_called_stuck():
    ch = pressure_channel()
    assert all(ch.update(101.0)[0] == [] for _ in range(50))


_jitter = random.Random(11)


def _obs(heater_draw: float, pressure: float = 101.0):
    # tiny jitter so the existing temperature and CO2 stuck checks see live sensors
    j = _jitter.uniform(-1e-3, 1e-3)
    return {"time.hour_of_day": 3.0, "power.store_level": 100000.0, "power.heater_draw": heater_draw,
            "gh.temp_C": 22.0 + j, "gh.co2_ppm": 1200.0 + j, "gh.o2_frac": 0.21 + j * 1e-3,
            "gh.pressure_kPa": pressure + j,
            "co2.injected_mol": 0.0, "crop.s0.co2_inhaled_mol": 0.0}


def test_heater_at_half_output_is_degraded_not_burnout():
    mon = AtmosphereTemperatureMonitor()
    mon.update(_obs(0.0), {"cmd.heater_W": 1500.0})
    assert mon.update(_obs(1500.0), {"cmd.heater_W": 1500.0})["actuator_faults"] == []
    out = mon.update(_obs(750.0), {"cmd.heater_W": 1500.0})
    assert out["actuator_faults"] == ["heater_degraded"] and out["temp_faults"] == []
    assert mon.update(_obs(0.0), {"cmd.heater_W": 1500.0})["actuator_faults"] == ["heater_burnout"]


def test_heater_limited_by_an_empty_battery_is_not_degraded():
    mon = AtmosphereTemperatureMonitor()
    mon.update(_obs(0.0), {"cmd.heater_W": 3000.0})
    assert mon.update({**_obs(1200.0), "power.store_level": 1200.0})["actuator_faults"] == []


def test_biased_pressure_does_not_rescale_the_co2_model():
    mon = AtmosphereTemperatureMonitor()
    for _ in range(5):
        before = mon.update(_obs(0.0), {"cmd.heater_W": 1500.0})
    out = mon.update(_obs(0.0, pressure=106.0), {"cmd.heater_W": 1500.0})
    assert out["pressure_faults"] == ["high_bias"] and out["co2_faults"] == []
    # +5 kPa taken at face value would lower the model by ~5 %, about 57 ppm
    assert abs(out["co2_model_est_ppm"] - before["co2_model_est_ppm"]) < 10.0

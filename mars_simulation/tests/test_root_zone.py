import statistics

import pytest

from interface import Fault
from sensors import ROOT_ZONE_KEY, RootZoneSensor


def fault(kind, tick=0, duration=None, **params):
    return Fault(tick=tick, kind=kind, target=ROOT_ZONE_KEY, params=params, duration=duration)


def readings(sensor, true_L, ticks, faults=()):
    return [sensor.read(true_L, t, [f for f in faults if f.active_at(t)]) for t in range(ticks)]


# sensor

def test_nominal_reading_is_unbiased_with_the_set_noise():
    r = readings(RootZoneSensor(sigma_L=0.2), 5.0, 2000)
    assert statistics.fmean(r) == pytest.approx(5.0, abs=0.02)
    assert statistics.stdev(r) == pytest.approx(0.2, rel=0.1)


def test_same_seed_gives_the_same_readings():
    assert readings(RootZoneSensor(seed=3), 5.0, 50) == readings(RootZoneSensor(seed=3), 5.0, 50)


def test_readings_are_clipped_to_the_tank():
    s = RootZoneSensor(capacity_L=500.0, sigma_L=1.0)
    assert min(readings(s, 0.0, 200)) == 0.0
    assert max(readings(s, 500.0, 200)) == 500.0


def test_bias_and_drift():
    s = RootZoneSensor(sigma_L=0.0)
    assert readings(s, 5.0, 3, [fault("bias", offset=-2.0)]) == [3.0, 3.0, 3.0]
    s = RootZoneSensor(sigma_L=0.0)
    assert readings(s, 5.0, 4, [fault("drift", tick=1, rate=0.5)]) == [5.0, 5.5, 6.0, 6.5]


def test_extra_noise_adds_to_the_baseline():
    r = readings(RootZoneSensor(sigma_L=0.3), 5.0, 2000, [fault("noise", sigma=0.4)])
    assert statistics.stdev(r) == pytest.approx(0.5, rel=0.1)


def test_stuck_repeats_the_last_reading_then_recovers():
    s = RootZoneSensor(sigma_L=0.0)
    out = [s.read(level, t, [f for f in [fault("stuck", tick=2, duration=2)] if f.active_at(t)])
           for t, level in enumerate([5.0, 6.0, 7.0, 8.0, 9.0])]
    assert out == [5.0, 6.0, 6.0, 6.0, 9.0]


def test_dropout_reports_none_and_keeps_the_last_good_reading():
    s = RootZoneSensor(sigma_L=0.0)
    out = readings(s, 5.0, 3, [fault("dropout", tick=1, duration=1)])
    assert out == [5.0, None, 5.0]
    assert s.last == 5.0


def test_faults_on_other_keys_are_ignored():
    s = RootZoneSensor(sigma_L=0.0)
    other = Fault(tick=0, kind="bias", target="water.potable_L", params={"offset": 100.0})
    assert readings(s, 5.0, 2, [other]) == [5.0, 5.0]


def test_unknown_fault_kind_is_an_error():
    with pytest.raises(ValueError, match="unknown root-zone sensor fault"):
        RootZoneSensor().read(5.0, 0, [fault("explode")])


# monitor: sensor vs estimator, on a FakePlant cycle with the irrigation controller

from controllers import IrrigationController  # noqa: E402
from estimators import ReservoirEstimator, RootZoneMonitor  # noqa: E402
from interface import FakePlant, TickLogger, run  # noqa: E402

SIGMA = 0.1


@pytest.fixture(scope="module")
def cycle():
    def atmo(obs):
        return {"cmd.crop_power_W": 10.0, "cmd.co2_inject_mol": 0.6 if obs["gh.co2_ppm"] < 1200 else 0.0}
    log = TickLogger()
    run(FakePlant(), {"irrigation": IrrigationController(), "atmo": atmo}, 700, logger=log)
    return log.records


def monitor_run(records, faults=(), degrade_from=None):
    """Replays a logged cycle through estimator, sensor and monitor. Returns per-tick rows."""
    est, sensor, mon, cap, rows = ReservoirEstimator(), RootZoneSensor(sigma_L=SIGMA), RootZoneMonitor(SIGMA), 0.0, []
    for rec in records:
        e = est.update(rec["obs"], cap)
        cap = rec["applied"]["cmd.crop_water_L"]
        if degrade_from is not None and rec["tick"] >= degrade_from:
            e = dict(e, **{"est.degraded": True})
        t = rec["tick"]
        true_L = rec["truth"]["crop.s0.reservoir_L"]
        reading = sensor.read(true_L, t, [f for f in faults if f.active_at(t)])
        out = mon.update(reading, e, rec["obs"]["gh.co2_ppm"])
        rows.append(dict(out, tick=t, true_L=true_L, est_L=e["est.reservoir_L"], sensor_L=reading))
    return rows


def first_tick_with(rows, label, after=0):
    return next((r["tick"] for r in rows if r["tick"] >= after and label in r["rz.faults"]), None)


def test_no_false_alarms_on_a_nominal_cycle(cycle):
    rows = monitor_run(cycle)
    assert [r["tick"] for r in rows if r["rz.faults"]] == []
    assert all(r["rz.trusted"] == "both" for r in rows)


def test_fused_level_beats_the_sensor_alone(cycle):
    # On FakePlant the estimator is exact (it shares the plant's equations), so fused vs
    # estimate can only be judged live; here the fused level must beat the raw sensor.
    rows = monitor_run(cycle)[30:]
    rms = lambda key: statistics.fmean((r[key] - r["true_L"]) ** 2 for r in rows) ** 0.5  # noqa: E731
    assert rms("rz.level_L") < rms("sensor_L")


@pytest.mark.parametrize("kind,params,max_delay", [
    ("bias", {"offset": -2.0}, 3),
    ("bias", {"offset": 3.0}, 3),
    ("drift", {"rate": 0.05}, 60),
])
def test_offset_faults_are_caught_and_the_estimate_takes_over(cycle, kind, params, max_delay):
    f = fault(kind, tick=300, duration=120, **params)
    rows = monitor_run(cycle, [f])
    caught = first_tick_with(rows, "offset", after=300)
    assert caught is not None and caught - 300 <= max_delay
    assert first_tick_with(rows, "offset") == caught, "no alarm before the fault"
    during = [r for r in rows if caught <= r["tick"] < 420]
    assert all(r["rz.trusted"] == "estimate" for r in during)
    assert all(abs(r["rz.level_L"] - r["true_L"]) < 1.0 for r in during)
    assert not rows[-1]["rz.faults"], "alarm clears after the fault ends"


def test_stuck_sensor_is_caught_within_stuck_ticks(cycle):
    rows = monitor_run(cycle, [fault("stuck", tick=300, duration=120)])
    assert first_tick_with(rows, "stuck") - 300 <= 3
    assert not rows[-1]["rz.faults"]


def test_dropout_falls_back_to_the_estimate(cycle):
    rows = monitor_run(cycle, [fault("dropout", tick=300, duration=10)])
    during = [r for r in rows if 300 <= r["tick"] < 310]
    assert all(r["rz.faults"] == ["dropout"] and r["rz.trusted"] == "estimate" for r in during)


def test_broken_estimator_is_blamed_not_the_sensor(cycle):
    rows = monitor_run(cycle, degrade_from=300)
    after = [r for r in rows if r["tick"] >= 300]
    assert all(r["rz.faults"] == ["estimator"] and r["rz.trusted"] == "sensor" for r in after)
    assert all(abs(r["rz.level_L"] - r["true_L"]) < 0.5 for r in after)

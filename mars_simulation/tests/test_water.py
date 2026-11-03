import pytest

from controllers import IrrigationController
from estimators import ReservoirEstimator
from interface import FakePlant, Fault, TickLogger, run


def atmo(obs):
    return {"cmd.crop_power_W": 10.0, "cmd.co2_inject_mol": 0.6 if obs["gh.co2_ppm"] < 1200 else 0.0}


def drawn(log):
    return sum(r["truth"]["water.crop_draw_L"] for r in log.records)


def true_res(log, skip=30):
    return [r["truth"]["crop.s0.reservoir_L"] for r in log.records[skip:]]


def run_cycle(irrigation, ticks=700, **kw):
    log = TickLogger()
    final = run(FakePlant(), {"irrigation": irrigation, "atmo": atmo}, ticks, logger=log, **kw)
    return final, log


# estimator

def score_estimator(log):
    est, cap, errs = ReservoirEstimator(), 0.0, []
    for r in log.records:
        e = est.update(r["obs"], cap)
        cap = r["applied"]["cmd.crop_water_L"]
        errs.append(e["est.reservoir_L"] - r["truth"]["crop.s0.reservoir_L"])
    return est, errs


@pytest.mark.parametrize("cap", [20.0, 0.4, 0.15])
def test_estimator_tracks_true_reservoir(cap):
    # full reservoir, small buffer, and running dry
    _, log = run_cycle(lambda obs: {"cmd.crop_water_L": cap})
    _, errs = score_estimator(log)
    assert max(abs(e) for e in errs) < 0.5, max(errs, key=abs)


def test_estimator_uses_exact_refills_when_the_cap_does_not_bind():
    _, log = run_cycle(lambda obs: {"cmd.crop_water_L": 20.0}, ticks=200)
    est, cap = ReservoirEstimator(incorporated_per_co2=0.3), 0.0  # start with a wrong ratio
    exact = 0
    for r in log.records:
        e = est.update(r["obs"], cap)
        cap = 20.0
        exact += e["est.exact"]
    assert exact > 100
    assert est.r == pytest.approx(0.61, abs=0.02), "ratio is learned from consecutive full refills"


def test_estimator_ignores_a_draw_cut_by_someone_else():
    est = ReservoirEstimator()
    base = {"gh.vapor_mol": 40.0, "water.dehumidified_L": 0.0, "water.potable_L": 900.0,
            "crop.s0.co2_inhaled_mol": 0.0, "crop.s0.reservoir_capacity_L": 500.0, "water.crop_draw_L": 0.0}
    est.update(base, 0.0)
    est.update(dict(base, **{"water.crop_draw_L": 1.0}), 1.0)
    # controller asked for 1.0 but a supervisor let only 0.2 through: not a full refill
    e = est.update(dict(base, **{"water.crop_draw_L": 0.2}), 1.0)
    assert not e["est.exact"]
    assert e["est.reservoir_L"] == pytest.approx(1.2)


def test_estimator_survives_dropouts():
    est = ReservoirEstimator()
    obs = {"gh.vapor_mol": 40.0, "water.dehumidified_L": 0.1, "water.potable_L": 900.0,
           "crop.s0.co2_inhaled_mol": 0.1, "crop.s0.reservoir_capacity_L": 500.0, "water.crop_draw_L": 0.5}
    est.update(obs, 0.5)
    est.update(obs, 0.5)
    e = est.update(dict(obs, **{"water.crop_draw_L": None, "gh.vapor_mol": None}), 0.5)
    assert e["est.degraded"]
    assert e["est.reservoir_L"] >= 0.0


# controller

def test_controller_saves_water_without_losing_yield():
    full, full_log = run_cycle(lambda obs: {"cmd.crop_water_L": 20.0})
    ctl, ctl_log = run_cycle(IrrigationController())
    assert ctl["crop.biomass_store_kg"] == pytest.approx(full["crop.biomass_store_kg"])
    assert drawn(ctl_log) < 0.3 * drawn(full_log)
    assert min(true_res(ctl_log)) > 1.0, "never runs the reservoir dry"
    assert ctl["water.potable_L"] > full["water.potable_L"] + 400


def test_controller_runs_the_recycler_on_waiting_dirty_water():
    _, log = run_cycle(IrrigationController())
    late = log.records[-1]
    assert late["applied"]["cmd.recycler_power_W"] > 0
    assert sum(r["truth"]["water.recycled_L"] for r in log.records) > 50
    assert late["truth"]["water.dirty_L"] < 2.0


def test_controller_rations_when_the_tank_is_low():
    ctl = IrrigationController(tank_reserve_L=100.0)
    obs = {"gh.vapor_mol": 40.0, "water.dehumidified_L": 0.4, "water.potable_L": 900.0,
           "crop.s0.co2_inhaled_mol": 0.3, "crop.s0.reservoir_capacity_L": 500.0,
           "water.crop_draw_L": 0.0, "water.dirty_L": 0.0}
    ctl(obs)
    for _ in range(50):
        ctl(dict(obs, **{"water.crop_draw_L": ctl.last_cap}))
    normal = ctl.buffer_hours * ctl.u_avg
    low = ctl(dict(obs, **{"water.potable_L": 50.0, "water.crop_draw_L": ctl.last_cap}))
    assert low["cmd.crop_water_L"] < ctl.u_avg + ctl.kp * normal


def test_broken_recycler_costs_tank_water_not_crop():
    broken = Fault(tick=300, kind="malfunction", target="Water_Recycler",
                   params={"intensity": "SEVERE_MALF", "length": "TEMPORARY_MALF"}, duration=50)
    final, log = run_cycle(IrrigationController(), fault_schedule=[broken])
    ok, _ = run_cycle(IrrigationController())
    assert final["crop.biomass_store_kg"] == pytest.approx(ok["crop.biomass_store_kg"])
    assert final["water.dirty_L"] > 50, "BioSim never restores a malfunctioned recycler"
    assert min(true_res(log)) > 1.0


def test_dirty_water_dropout_holds_last_recycler_setting():
    ctl = IrrigationController()
    obs = {"gh.vapor_mol": 40.0, "water.dehumidified_L": 0.0, "water.potable_L": 900.0,
           "crop.s0.co2_inhaled_mol": 0.0, "crop.s0.reservoir_capacity_L": 500.0,
           "water.crop_draw_L": 0.0, "water.dirty_L": 2.0}
    first = ctl(obs)["cmd.recycler_power_W"]
    assert ctl(dict(obs, **{"water.dirty_L": None}))["cmd.recycler_power_W"] == first

import pytest

from interface import FakePlant, Fault, TickLogger, read_log, run
from interface.schema import is_truth_only


def water_controller(obs):
    return {"cmd.crop_water_L": 2.0}


def lamp_controller(obs):
    return {"cmd.crop_power_W": 3000.0}


def test_full_lettuce_cycle_runs_and_logs(tmp_path):
    path = tmp_path / "run.jsonl"
    with TickLogger(path) as log:
        final = run(FakePlant(), {"water": water_controller, "lamps": lamp_controller}, 672, logger=log)
    records = read_log(path)
    assert len(records) == 672
    assert final["time.tick"] == 672
    assert final["crop.biomass_store_kg"] > 0
    first = records[0]
    assert set(first) == {"tick", "truth", "obs", "commands", "applied", "faults", "wall_ms"}
    assert first["commands"] == {"water": {"cmd.crop_water_L": 2.0}, "lamps": {"cmd.crop_power_W": 3000.0}}


def test_controllers_never_see_truth_only_keys():
    seen = []
    run(FakePlant(), {"spy": lambda obs: seen.append(set(obs)) or {}}, 3)
    assert seen and all(not any(is_truth_only(k) for k in keys) for keys in seen)


def test_conflicting_controllers_need_a_supervisor():
    with pytest.raises(ValueError, match="both command cmd.crop_water_L"):
        run(FakePlant(), {"a": water_controller, "b": water_controller}, 1)


def test_dust_storm_reduces_solar_then_clears():
    storm = Fault(tick=10, kind="malfunction", target="Solar_Array",
                  params={"intensity": "SEVERE_MALF", "length": "PERMANENT_MALF"}, duration=20)
    log = TickLogger()
    plant = FakePlant()
    run(plant, {}, 50, fault_schedule=[storm], logger=log)
    active = [r["tick"] for r in log.records if r["faults"]]
    assert active == list(range(10, 30))
    assert plant.solar_scale == pytest.approx(1.0)
    assert log.records[15]["truth"]["faults.active"]


def test_python_side_faults_reach_the_layers_via_context():
    seen = []

    def sensors(truth, ctx):
        seen.append([f.target for f in ctx.active_faults])
        return truth

    noise = Fault(tick=2, kind="noise", target="gh.co2_ppm", params={"sigma": 50}, duration=2)
    run(FakePlant(), {}, 6, sensor_layer=sensors, fault_schedule=[noise])
    assert seen == [[], [], ["gh.co2_ppm"], ["gh.co2_ppm"], [], []]


def test_supervisor_sees_who_asked():
    def halve_lamps(obs, per_controller, ctx):
        merged = {}
        for cmds in per_controller.values():
            merged.update(cmds)
        merged["cmd.crop_power_W"] = merged["cmd.crop_power_W"] / 2
        return merged

    log = TickLogger()
    run(FakePlant(), {"lamps": lamp_controller}, 2, supervisor=halve_lamps, logger=log)
    assert log.records[0]["applied"] == {"cmd.crop_power_W": 1500.0}


# ctx.truth: sensors can measure truth-only values, controllers still can't see them

RESERVOIR = "crop.s0.reservoir_L"


def test_sensor_layer_reads_truth_only_values_from_ctx():
    seen = []

    def level_sensor(state, ctx):
        assert RESERVOIR not in state, "the state argument stays public"
        return dict(state, **{"meas.reservoir_L": ctx.truth[RESERVOIR]})

    log = TickLogger()
    run(FakePlant(), {"spy": lambda obs: seen.append(obs["meas.reservoir_L"]) or {}}, 5,
        sensor_layer=level_sensor, logger=log)
    assert seen == [r["truth"][RESERVOIR] for r in log.records]


def test_truth_only_keys_copied_by_a_sensor_layer_are_still_stripped():
    seen = []
    leaky = lambda state, ctx: dict(ctx.truth)  # noqa: E731
    run(FakePlant(), {"spy": lambda obs: seen.append(set(obs)) or {}}, 3, sensor_layer=leaky)
    assert seen and all(not any(is_truth_only(k) for k in keys) for keys in seen)


def test_ctx_truth_is_read_only():
    def vandal(state, ctx):
        ctx.truth[RESERVOIR] = -1.0
        return state

    with pytest.raises(TypeError):
        run(FakePlant(), {}, 1, sensor_layer=vandal)

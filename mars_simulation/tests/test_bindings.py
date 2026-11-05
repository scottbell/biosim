import pytest

from interface import bindings
from interface.schema import COMMAND_KEYS, STATE_KEYS, TRUTH_ONLY_KEYS, is_state_key, public, shelf_key

from conftest import raw_fixtures


@pytest.mark.parametrize("name,raw_state", raw_fixtures(), ids=lambda x: x if isinstance(x, str) else "")
def test_every_schema_key_is_produced(name, raw_state):
    state = bindings.to_state(raw_state)
    for key in list(STATE_KEYS) + list(TRUTH_ONLY_KEYS):
        assert key in state, key
    assert shelf_key(0, "harvest_interval") in state
    assert all(is_state_key(k) for k in state)


@pytest.mark.parametrize("name,raw_state", raw_fixtures(), ids=lambda x: x if isinstance(x, str) else "")
def test_config_validates(name, raw_state):
    assert bindings.validate(raw_state) == []


def test_derived_values(raw):
    s = bindings.to_state(raw)
    assert s["gh.co2_ppm"] == pytest.approx(1200.0)
    assert s["water.crop_draw_L"] == pytest.approx(20.0)
    assert s["time.hour_of_day"] == 0.0
    assert "temperature" not in " ".join(s)


def test_truth_only_keys_are_stripped(raw):
    obs = public(bindings.to_state(raw))
    assert not set(obs) & set(TRUTH_ONLY_KEYS)


def test_active_faults_only_lists_performed(raw):
    raw["modules"]["Solar_Array"]["malfunctions"] = [
        {"id": 3, "name": "x", "intensity": "SEVERE_MALF", "length": "PERMANENT_MALF",
         "performed": True, "tickToMalfunction": 5, "doneEnoughRepairWork": False},
        {"id": 4, "name": "y", "intensity": "LOW_MALF", "length": "TEMPORARY_MALF",
         "performed": False, "tickToMalfunction": 50, "doneEnoughRepairWork": False},
    ]
    faults = bindings.to_state(raw)["faults.active"]
    assert [f["id"] for f in faults] == [3]


@pytest.mark.parametrize("mutate,expect", [
    (lambda r: r["globals"].update(tickLength=0.5), "tickLength"),
    (lambda r: r["globals"].update(simulationIsPaused=False), "not paused"),
    (lambda r: r["modules"].pop("CO2_Injector"), "missing"),
    (lambda r: r["modules"]["Lettuce_Bed"]["consumers"][1]["rates"].update(maxFlowRates=[50]), "maxFlowRates"),
    (lambda r: r["modules"]["Lettuce_Bed"]["properties"]["shelves"][0].pop("waterLevel"), "unpatched"),
    (lambda r: r["modules"]["CO2_Injector"]["consumers"][0].update(connections=["Greenhouse.CO2Store"]),
     "environment"),
])
def test_validate_catches_config_mistakes(raw, mutate, expect):
    mutate(raw)
    problems = bindings.validate(raw)
    assert any(expect in p for p in problems), problems


def test_every_command_has_a_binding():
    assert set(bindings.COMMAND_BINDINGS) == set(COMMAND_KEYS)


def test_water_keys(raw):
    s = bindings.to_state(raw)
    assert s["water.dehumidified_L"] == pytest.approx(10.0 * 0.01801524)
    assert s["crop.s0.reservoir_capacity_L"] == 500.0
    assert s["crop.s0.reservoir_L"] == 20.0
    obs = public(s)
    assert "crop.s0.reservoir_L" not in obs, "true reservoir level must stay hidden from controllers"
    assert "crop.s0.reservoir_capacity_L" in obs


def test_irrigation_draws_from_the_potable_tank():
    assert bindings.COMMAND_BINDINGS["cmd.crop_water_L"][0].flow_type == "PotableWater"


def test_unpatched_server_fails_with_a_clear_message(raw):
    raw["modules"]["Lettuce_Bed"]["properties"]["shelves"][0].pop("waterLevel")
    with pytest.raises(bindings.BindingError, match="unpatched"):
        bindings.to_state(raw)


def test_missing_module_error_is_readable(raw):
    raw["modules"].pop("Grey_Water_Store")
    with pytest.raises(bindings.BindingError, match="Grey_Water_Store"):
        bindings.to_state(raw)

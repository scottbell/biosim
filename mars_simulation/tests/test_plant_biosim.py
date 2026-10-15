"""BioSimPlant against a recording stand-in for BioSimClient (no server needed)."""
import pytest

from interface import BioSimPlant, Fault

from conftest import synthetic_raw


class RecordingClient:
    def __init__(self):
        self.calls = []
        self.sim_id = None
        self.raw = synthetic_raw()

    def start(self, xml):
        assert "<biosim" in xml
        self.sim_id = 1
        return 1

    def state(self):
        return self.raw

    def tick(self):
        self.raw["globals"]["ticksGoneBy"] += 1
        return self.raw["globals"]["ticksGoneBy"]

    def set_consumer(self, module, flow_type, desired, connections=None):
        self.calls.append(("consumer", module, flow_type, desired))

    def set_producer(self, module, flow_type, desired, connections=None):
        self.calls.append(("producer", module, flow_type, desired))

    def add_malfunction(self, module, intensity, length, tick=None):
        self.calls.append(("malf", module, intensity.value, length.value))
        return 7

    def clear_malfunction(self, module, malfunction_id=None):
        self.calls.append(("clear", module, malfunction_id))


@pytest.fixture
def plant():
    p = BioSimPlant(RecordingClient())
    p.reset()
    return p


def test_commands_map_to_rest_targets(plant):
    plant.apply({"cmd.crop_water_L": 5, "cmd.crop_power_W": 1000, "cmd.co2_inject_mol": 0.5})
    assert plant.client.calls == [
        ("consumer", "Lettuce_Bed", "GreyWater", [5.0]),
        ("consumer", "Lettuce_Bed", "Power", [1000.0]),
        ("consumer", "CO2_Injector", "CO2", [0.5]),
    ]


def test_unchanged_commands_are_not_resent(plant):
    plant.apply({"cmd.crop_water_L": 5})
    plant.apply({"cmd.crop_water_L": 5})
    plant.apply({"cmd.crop_water_L": 6})
    assert [c[3] for c in plant.client.calls] == [[5.0], [6.0]]


def test_unknown_command_key_is_rejected(plant):
    with pytest.raises(KeyError, match="cmd.crop_watr_L"):
        plant.apply({"cmd.crop_watr_L": 5})


def test_bad_config_fails_at_reset():
    client = RecordingClient()
    client.raw["globals"]["tickLength"] = 2
    with pytest.raises(RuntimeError, match="tickLength"):
        BioSimPlant(client).reset()


def test_malfunction_inject_and_clear_use_server_id(plant):
    storm = Fault(tick=0, kind="malfunction", target="Solar_Array",
                  params={"intensity": "SEVERE_MALF", "length": "PERMANENT_MALF"}, duration=10)
    plant.inject(storm)
    plant.clear(storm)
    assert plant.client.calls == [("malf", "Solar_Array", "SEVERE_MALF", "PERMANENT_MALF"),
                                  ("clear", "Solar_Array", 7)]


def test_python_side_faults_do_not_touch_biosim(plant):
    plant.inject(Fault(tick=0, kind="noise", target="gh.co2_ppm", params={"sigma": 50}))
    assert plant.client.calls == []

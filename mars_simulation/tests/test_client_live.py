"""Runs only with a BioSim server up:  pytest -m live"""
import pytest
import requests

from interface import BioSimClient, BioSimError, BioSimPlant

pytestmark = pytest.mark.live


@pytest.fixture(scope="module")
def client():
    c = BioSimClient()
    try:
        c.list_sims()
    except requests.ConnectionError:
        pytest.skip("BioSim server not running on localhost:8009")
    return c


def test_start_validate_and_tick(client):
    plant = BioSimPlant(client)
    state = plant.reset()
    assert state["time.tick"] == 0
    state = plant.step()
    assert state["time.tick"] == 1


def test_command_reaches_biosim(client):
    plant = BioSimPlant(client)
    plant.reset()
    plant.apply({"cmd.crop_water_L": 7.0})
    bed = client.module("Lettuce_Bed")
    grey = next(d for d in bed["consumers"] if d["type"] == "GreyWater")
    assert grey["rates"]["desiredFlowRates"] == [7.0]


def test_malfunction_round_trip(client):
    plant = BioSimPlant(client)
    plant.reset()
    malf_id = client.add_malfunction("Solar_Array", "SEVERE_MALF", "PERMANENT_MALF")
    assert any(m["id"] == malf_id for m in client.malfunctions("Solar_Array"))
    client.clear_malfunction("Solar_Array", malf_id)
    assert not client.malfunctions("Solar_Array")


def test_server_errors_carry_biosim_message(client):
    plant = BioSimPlant(client)
    plant.reset()
    with pytest.raises(BioSimError, match="not found"):
        client.set_consumer("No_Such_Module", "Power", [1.0])

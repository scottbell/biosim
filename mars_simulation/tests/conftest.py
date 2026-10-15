"""Builds a synthetic raw state in the exact shape SimulationController.java serializes
(buildModuleInfo, buildDefinitionInfo, buildEnvironmentInfo, buildStoreInfo,
buildBiomassShelvesInfo) for configs/mars_lettuce.biosim. A real capture from
scripts/smoke_test.py lands in tests/fixtures/state_sample.json and is tested too.
"""
from __future__ import annotations

import copy
import json
from pathlib import Path

import pytest

REAL_FIXTURE = Path(__file__).parent / "fixtures" / "state_sample.json"


def _def(flow_type, desired, max_, conns, actual=None):
    return {"type": flow_type,
            "rates": {"maxFlowRates": [max_], "desiredFlowRates": [desired],
                      "actualFlowRates": [desired if actual is None else actual],
                      "initialFlowRates": [desired]},
            "connections": conns}


def _store(name, mtype, level, cap):
    return {"moduleName": name, "moduleType": mtype,
            "properties": {"currentLevel": level, "currentCapacity": cap, "overflow": 0.0, "isPipe": False},
            "malfunctions": []}


def synthetic_raw():
    total = 2050.0
    return {
        "globals": {"myID": 1, "simulationIsPaused": True, "simulationStarted": True,
                    "simulationEnded": False, "ticksGoneBy": 0, "runTillN": -1,
                    "runTillCrewDeath": False, "runTillPlantDeath": False, "looping": False,
                    "driverStutterLength": 0, "tickLength": 1.0},
        "modules": {
            "Greenhouse": {"moduleName": "Greenhouse", "moduleType": "SimEnvironment",
                           "properties": {"temperature": 23.0, "temperatureInKelvin": 296.15,
                                          "lightIntensity": 0.0, "currentVolume": 50000.0,
                                          "initialVolume": 50000.0, "dayLength": 24.66,
                                          "hourOfDayStart": 0.0, "maxLumens": 50000.0,
                                          "relativeHumidity": 0.7, "airLockVolume": 3.7,
                                          "totalPressure": 101.0, "totalMoles": total,
                                          "dangerousOxygenThreshold": 1.0, "leakRate": 0.0,
                                          "initialTotalPressure": 101.0,
                                          "o2Moles": 0.21 * total, "co2Moles": 0.0012 * total,
                                          "otherMoles": 0.0, "vaporMoles": 0.02 * total,
                                          "nitrogenMoles": 0.7688 * total},
                           "malfunctions": []},
            "Dehumidifier": {"moduleName": "Dehumidifier", "moduleType": "Dehumidifier",
                             "consumers": [_def("Air", 1000, 1000, ["Greenhouse"])],
                             "producers": [_def("DirtyWater", 1000, 1000, ["Dirty_Water_Store"], 0)],
                             "malfunctions": []},
            "CO2_Store": _store("CO2_Store", "CO2Store", 200.0, 500.0),
            "Potable_Water_Store": _store("Potable_Water_Store", "PotableWaterStore", 1000.0, 1000.0),
            "Grey_Water_Store": _store("Grey_Water_Store", "GreyWaterStore", 2000.0, 2000.0),
            "Dirty_Water_Store": _store("Dirty_Water_Store", "DirtyWaterStore", 0.0, 5000.0),
            "Power_Store": _store("Power_Store", "PowerStore", 100000.0, 200000.0),
            "Biomass_Store": _store("Biomass_Store", "BiomassStore", 0.0, 1000.0),
            "Solar_Array": {"moduleName": "Solar_Array", "moduleType": "SolarPowerPS",
                            "consumers": [{"type": "Light"}],
                            "producers": [_def("Power", 20000, 20000, ["Power_Store"], 0)],
                            "malfunctions": []},
            "Lettuce_Bed": {"moduleName": "Lettuce_Bed", "moduleType": "BiomassPS",
                            "consumers": [_def("Power", 2000, 20000, ["Power_Store"]),
                                          _def("PotableWater", 0, 0, ["Potable_Water_Store"]),
                                          _def("GreyWater", 20, 200, ["Grey_Water_Store"]),
                                          _def("Air", 0, 0, ["Greenhouse"])],
                            "producers": [_def("DirtyWater", 100, 100, ["Dirty_Water_Store"], 0),
                                          _def("Biomass", 100, 100, ["Biomass_Store"], 0),
                                          _def("Air", 0, 0, ["Greenhouse"])],
                            "properties": {"shelves": [{"plantType": "LETTUCE", "cropAreaUsed": 10.0,
                                                        "cropAreaTotal": 10.0,
                                                        "timeTillCanopyClosure": 20.0,
                                                        "harvestInterval": 28.0, "ppfNeeded": 295.14,
                                                        "molesOfCO2Inhaled": 0.0}]},
                            "malfunctions": []},
            "CO2_Injector": {"moduleName": "CO2_Injector", "moduleType": "Injector",
                             "consumers": [_def("CO2", 0, 5, ["CO2_Store"])],
                             "producers": [_def("CO2", 5, 5, ["Greenhouse.CO2Store"], 0)],
                             "malfunctions": []},
        },
    }


def raw_fixtures():
    out = [("synthetic", synthetic_raw())]
    if REAL_FIXTURE.exists():
        out.append(("captured", json.loads(REAL_FIXTURE.read_text())))
    return out


@pytest.fixture
def raw():
    return copy.deepcopy(synthetic_raw())


def pytest_configure(config):
    config.addinivalue_line("markers", "live: needs a BioSim server on localhost:8009")

"""The only file that knows BioSim module names and JSON layout.

Raw state (GET /api/simulation/{id}) -> flat state dict, and command keys -> REST targets.
If configs/mars_lettuce.biosim changes a moduleName, change it here and nowhere else.
JSON layout source: SimulationController.java buildModuleInfo / buildDefinitionInfo.
"""
from __future__ import annotations

from dataclasses import dataclass
from typing import Any, Callable, Dict, List

from .schema import COMMAND_KEYS, SHELF_FIELDS, shelf_key

GREENHOUSE = "Greenhouse"
CROP_BED = "Lettuce_Bed"
SOLAR = "Solar_Array"
POWER_STORE = "Power_Store"
POTABLE = "Potable_Water_Store"
GREY = "Grey_Water_Store"
DIRTY = "Dirty_Water_Store"
CO2_STORE = "CO2_Store"
CO2_INJECTOR = "CO2_Injector"
BIOMASS_STORE = "Biomass_Store"

REQUIRED_MODULES = [GREENHOUSE, CROP_BED, SOLAR, POWER_STORE, POTABLE, GREY, DIRTY,
                    CO2_STORE, CO2_INJECTOR, BIOMASS_STORE]

# Modules whose malfunctions are reported in faults.active.
FAULT_WATCH = REQUIRED_MODULES


class BindingError(KeyError):
    pass


@dataclass(frozen=True)
class FlowTarget:
    module: str
    side: str        # "consumers" or "producers"
    flow_type: str   # exactly as the state JSON "type" field spells it


# Each command writes desiredFlowRates[0] of every target. Targets must have exactly one
# connection, and none may connect to an environment (REST rejects those updates).
COMMAND_BINDINGS: Dict[str, List[FlowTarget]] = {
    "cmd.crop_water_L":   [FlowTarget(CROP_BED, "consumers", "GreyWater")],
    "cmd.crop_power_W":   [FlowTarget(CROP_BED, "consumers", "Power")],
    "cmd.co2_inject_mol": [FlowTarget(CO2_INJECTOR, "consumers", "CO2")],
}
assert set(COMMAND_BINDINGS) == set(COMMAND_KEYS)


def _module(raw: Dict[str, Any], name: str) -> Dict[str, Any]:
    try:
        return raw["modules"][name]
    except KeyError:
        raise BindingError(f"module {name!r} not in BioSim state; check config vs bindings.py") from None


def _props(raw: Dict[str, Any], name: str) -> Dict[str, Any]:
    return _module(raw, name).get("properties", {})


def find_definition(raw: Dict[str, Any], target: FlowTarget) -> Dict[str, Any]:
    for d in _module(raw, target.module).get(target.side, []):
        if d.get("type") == target.flow_type:
            return d
    raise BindingError(f"{target.module} has no {target.side[:-1]} of type {target.flow_type!r}")


def _actual(raw: Dict[str, Any], target: FlowTarget) -> float:
    return float(sum(find_definition(raw, target)["rates"]["actualFlowRates"]))


def _level(raw: Dict[str, Any], store: str) -> float:
    return float(_props(raw, store)["currentLevel"])


def _gas_frac(raw: Dict[str, Any], field: str) -> float:
    p = _props(raw, GREENHOUSE)
    total = float(p["totalMoles"])
    return float(p[field]) / total if total > 0 else 0.0


def _hour_of_day(raw: Dict[str, Any]) -> float:
    p = _props(raw, GREENHOUSE)
    g = raw["globals"]
    return (float(p["hourOfDayStart"]) + g["ticksGoneBy"] * float(g["tickLength"])) % float(p["dayLength"])


def _active_faults(raw: Dict[str, Any]) -> List[Dict[str, Any]]:
    out = []
    for name in FAULT_WATCH:
        for m in _module(raw, name).get("malfunctions", []):
            if m.get("performed"):
                out.append({"module": name, "id": m["id"], "intensity": m["intensity"],
                            "length": m["length"], "tick": m["tickToMalfunction"]})
    return out


STATE_READERS: Dict[str, Callable[[Dict[str, Any]], Any]] = {
    "time.tick":             lambda r: int(r["globals"]["ticksGoneBy"]),
    "time.hour_of_day":      _hour_of_day,
    "gh.co2_ppm":            lambda r: _gas_frac(r, "co2Moles") * 1e6,
    "gh.o2_frac":            lambda r: _gas_frac(r, "o2Moles"),
    "gh.rh":                 lambda r: float(_props(r, GREENHOUSE)["relativeHumidity"]),
    "gh.pressure_kPa":       lambda r: float(_props(r, GREENHOUSE)["totalPressure"]),
    "gh.light":              lambda r: float(_props(r, GREENHOUSE)["lightIntensity"]),
    "power.store_level":     lambda r: _level(r, POWER_STORE),
    "power.store_capacity":  lambda r: float(_props(r, POWER_STORE)["currentCapacity"]),
    "power.solar_out":       lambda r: _actual(r, FlowTarget(SOLAR, "producers", "Power")),
    "power.crop_draw":       lambda r: _actual(r, FlowTarget(CROP_BED, "consumers", "Power")),
    "water.potable_L":       lambda r: _level(r, POTABLE),
    "water.grey_L":          lambda r: _level(r, GREY),
    "water.dirty_L":         lambda r: _level(r, DIRTY),
    "water.crop_draw_L":     lambda r: (_actual(r, FlowTarget(CROP_BED, "consumers", "GreyWater"))
                                        + _actual(r, FlowTarget(CROP_BED, "consumers", "PotableWater"))),
    "co2.store_mol":         lambda r: _level(r, CO2_STORE),
    "co2.injected_mol":      lambda r: _actual(r, FlowTarget(CO2_INJECTOR, "consumers", "CO2")),
    "crop.biomass_store_kg": lambda r: _level(r, BIOMASS_STORE),
    "sim.ended":             lambda r: bool(r["globals"]["simulationEnded"]),
    "faults.active":         _active_faults,
}

_SHELF_JSON = {
    "time_till_canopy_closure": "timeTillCanopyClosure",
    "harvest_interval": "harvestInterval",
    "co2_inhaled_mol": "molesOfCO2Inhaled",
    "ppf_needed": "ppfNeeded",
}
assert set(_SHELF_JSON) == set(SHELF_FIELDS)


def to_state(raw: Dict[str, Any]) -> Dict[str, Any]:
    """Raw BioSim JSON -> flat truth dict. Raises BindingError if the config doesn't match."""
    state = {key: read(raw) for key, read in STATE_READERS.items()}
    for i, shelf in enumerate(_props(raw, CROP_BED).get("shelves", [])):
        for name, json_key in _SHELF_JSON.items():
            state[shelf_key(i, name)] = float(shelf[json_key])
    return state


def validate(raw: Dict[str, Any]) -> List[str]:
    """Config problems that would otherwise look like controller bugs. Empty list = OK."""
    problems = []
    g = raw.get("globals", {})
    if float(g.get("tickLength", -1)) != 1.0:
        problems.append(f"tickLength is {g.get('tickLength')}, must be 1 (crop model assumes 1 tick = 1 hour)")
    if not g.get("simulationIsPaused", False):
        problems.append("simulation is not paused; set startPaused=\"true\" or BioSim ticks on its own")
    missing = [m for m in REQUIRED_MODULES if m not in raw.get("modules", {})]
    if missing:
        problems.append(f"modules missing from config: {missing}")
        return problems
    for key, targets in COMMAND_BINDINGS.items():
        hi = COMMAND_KEYS[key].hi
        for t in targets:
            try:
                d = find_definition(raw, t)
            except BindingError as e:
                problems.append(f"{key}: {e}")
                continue
            if len(d.get("connections", [])) != 1:
                problems.append(f"{key}: {t.module}.{t.flow_type} must have exactly one connection")
            # env gas stores show up as "<EnvName>.CO2Store", which is not a registered module
            conn = d["connections"][0].split(".")[0] if d.get("connections") else None
            if conn in raw["modules"] and raw["modules"][conn]["moduleType"] == "SimEnvironment":
                problems.append(f"{key}: {t.module}.{t.flow_type} connects to an environment; REST can't update it")
            max_rate = d["rates"]["maxFlowRates"][0] if d["rates"]["maxFlowRates"] else 0
            if hi is not None and max_rate < hi:
                problems.append(f"{key}: maxFlowRates of {t.module}.{t.flow_type} is {max_rate}, "
                                f"below command range {hi}; raise it in the XML")
    if not _props(raw, CROP_BED).get("shelves"):
        problems.append(f"{CROP_BED} has no shelves")
    return problems

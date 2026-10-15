"""The contract everyone codes against: state keys, command keys, units, and fault records.

Controllers read keys from STATE_KEYS and write keys from COMMAND_KEYS. They never see
BioSim module names; that mapping lives only in bindings.py.
"""
from __future__ import annotations

from dataclasses import dataclass, field
from typing import Any, Dict, Optional


@dataclass(frozen=True)
class Key:
    unit: str
    description: str
    lo: Optional[float] = None
    hi: Optional[float] = None


STATE_KEYS: Dict[str, Key] = {
    "time.tick":             Key("tick", "ticks elapsed (1 tick = 1 hour)"),
    "time.hour_of_day":      Key("h", "hour within the Mars sol (dayLength in config)"),
    "gh.co2_ppm":            Key("ppm", "greenhouse CO2 mole fraction x 1e6"),
    "gh.o2_frac":            Key("-", "greenhouse O2 mole fraction"),
    "gh.rh":                 Key("-", "greenhouse relative humidity, 0-1"),
    "gh.pressure_kPa":       Key("kPa", "greenhouse total pressure"),
    "gh.light":              Key("lumens", "outside light used by the solar array (day/night shape)"),
    "power.store_level":     Key("W*tick", "energy in Power_Store"),
    "power.store_capacity":  Key("W*tick", "Power_Store capacity"),
    "power.solar_out":       Key("W", "solar array output this tick"),
    "power.crop_draw":       Key("W", "power actually drawn by the lettuce bed (lamps) this tick"),
    "water.potable_L":       Key("L", "potable water store level"),
    "water.grey_L":          Key("L", "grey water store level (irrigation tank)"),
    "water.dirty_L":         Key("L", "dirty water store level (dehumidifier output)"),
    "water.crop_draw_L":     Key("L", "water actually drawn by the lettuce bed this tick"),
    "co2.store_mol":         Key("mol", "CO2 tank level for injection"),
    "co2.injected_mol":      Key("mol", "CO2 actually injected this tick"),
    "crop.biomass_store_kg": Key("kg", "harvested biomass in Biomass_Store"),
}

# Per-shelf keys are crop.s{i}.<field>, i = shelf index in the config.
SHELF_FIELDS: Dict[str, Key] = {
    "time_till_canopy_closure": Key("days", "days until canopy closes"),
    "harvest_interval":         Key("days", "days from planting to harvest"),
    "co2_inhaled_mol":          Key("mol", "CO2 taken up by the shelf this tick"),
    "ppf_needed":               Key("umol/m2/s", "light the crop asks for"),
}

# Present in truth only. The loop strips these before the sensor layer, so controllers
# never see them. Fault detection is scored against them.
TRUTH_ONLY_KEYS: Dict[str, Key] = {
    "sim.ended":     Key("bool", "BioSim reports the run met an end criterion"),
    "faults.active": Key("list", "malfunctions BioSim has actually applied"),
}

COMMAND_KEYS: Dict[str, Key] = {
    "cmd.crop_water_L":   Key("L/tick", "max grey water the lettuce bed may draw per tick", 0, 200),
    "cmd.crop_power_W":   Key("W", "max power the lettuce bed (lamps) may draw per tick", 0, 20000),
    "cmd.co2_inject_mol": Key("mol/tick", "CO2 moved from CO2_Store into the greenhouse", 0, 5),
}


def shelf_key(i: int, name: str) -> str:
    if name not in SHELF_FIELDS:
        raise KeyError(f"unknown shelf field {name!r}")
    return f"crop.s{i}.{name}"


def is_state_key(key: str) -> bool:
    if key in STATE_KEYS or key in TRUTH_ONLY_KEYS:
        return True
    parts = key.split(".")
    return (len(parts) == 3 and parts[0] == "crop" and parts[1].startswith("s")
            and parts[1][1:].isdigit() and parts[2] in SHELF_FIELDS)


def public(truth: Dict[str, Any]) -> Dict[str, Any]:
    return {k: v for k, v in truth.items() if k not in TRUTH_ONLY_KEYS}


@dataclass
class Fault:
    """One scheduled fault. Every fault goes to the ground-truth log, whatever its kind.

    kind="malfunction" is executed by BioSim (target = module name, params = intensity/length).
    Any other kind (e.g. "noise", "dropout", "stuck") is executed by the Python sensor or
    actuator layer, which reads active faults from TickContext (target = a state or command key).
    duration=None means it stays active until the end of the run.
    """
    tick: int
    kind: str
    target: str
    params: Dict[str, Any] = field(default_factory=dict)
    duration: Optional[int] = None

    def active_at(self, tick: int) -> bool:
        if tick < self.tick:
            return False
        return self.duration is None or tick < self.tick + self.duration

    def to_dict(self) -> Dict[str, Any]:
        return {"tick": self.tick, "kind": self.kind, "target": self.target,
                "params": self.params, "duration": self.duration}

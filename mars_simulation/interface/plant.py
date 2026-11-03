"""Plant backends. Anything the loop runs against implements Plant.

BioSimPlant talks to the real server. FakePlant is a deterministic stand-in with the same
keys, so controllers, fault layers and the dashboard can be built without Docker.
Later, a HybridPlant (BioSim + Python thermal model) slots in here; see plant_next_steps.md.
"""
from __future__ import annotations

import math
from pathlib import Path
from typing import Any, Dict, Optional

try:
    from typing import Protocol
except ImportError:  # pragma: no cover
    from typing_extensions import Protocol

from . import bindings
from .client import BioSimClient, Intensity, Length
from .schema import COMMAND_KEYS, Fault, shelf_key

DEFAULT_CONFIG = Path(__file__).resolve().parent.parent / "configs" / "mars_lettuce.biosim"


class Plant(Protocol):
    def reset(self) -> Dict[str, Any]: ...
    def read(self) -> Dict[str, Any]: ...
    def apply(self, commands: Dict[str, float]) -> None: ...
    def step(self) -> Dict[str, Any]: ...
    def inject(self, fault: Fault) -> None: ...
    def clear(self, fault: Fault) -> None: ...


def _check_commands(commands: Dict[str, float]) -> None:
    unknown = set(commands) - set(COMMAND_KEYS)
    if unknown:
        raise KeyError(f"unknown command keys {sorted(unknown)}; valid: {sorted(COMMAND_KEYS)}")


class BioSimPlant:
    def __init__(self, client: Optional[BioSimClient] = None, config_path: Path = DEFAULT_CONFIG):
        self.client = client or BioSimClient()
        self.config_path = Path(config_path)
        self.raw: Dict[str, Any] = {}
        self._last_sent: Dict[str, float] = {}
        self._malf_ids: Dict[int, int] = {}

    def reset(self) -> Dict[str, Any]:
        self.client.start(self.config_path.read_text())
        self.raw = self.client.state()
        problems = bindings.validate(self.raw)
        if problems:
            raise RuntimeError("Config does not match the interface:\n  " + "\n  ".join(problems))
        self._last_sent = {}
        self._malf_ids = {}
        return bindings.to_state(self.raw)

    def read(self) -> Dict[str, Any]:
        self.raw = self.client.state()
        return bindings.to_state(self.raw)

    def apply(self, commands: Dict[str, float]) -> None:
        _check_commands(commands)
        for key, value in commands.items():
            value = float(value)
            if self._last_sent.get(key) == value:
                continue
            for t in bindings.COMMAND_BINDINGS[key]:
                if t.side == "consumers":
                    self.client.set_consumer(t.module, t.flow_type, [value])
                else:
                    self.client.set_producer(t.module, t.flow_type, [value])
            self._last_sent[key] = value

    def step(self) -> Dict[str, Any]:
        self.client.tick()
        return self.read()

    def inject(self, fault: Fault) -> None:
        if fault.kind != "malfunction":
            return
        # started now (no tickToOccur) so the server returns an id we can clear later
        malf_id = self.client.add_malfunction(
            fault.target,
            Intensity(fault.params.get("intensity", Intensity.MEDIUM)),
            Length(fault.params.get("length", Length.TEMPORARY)),
        )
        if malf_id is not None:
            self._malf_ids[id(fault)] = malf_id

    def clear(self, fault: Fault) -> None:
        malf_id = self._malf_ids.pop(id(fault), None)
        if fault.kind == "malfunction" and malf_id is not None:
            self.client.clear_malfunction(fault.target, malf_id)




class FakePlant:
    """Toy dynamics with the same keys as BioSimPlant, for building and testing code without
    the server. The water path follows BioSim's rules (Shelf.gatherWater, Plant uptake,
    Dehumidifier, WaterRSLinear) with rates fitted to a live patched run on 2026-10-30:
    CO2 uptake ~0.30 mol/tick at maturity, transpiration 1.35 L and incorporation 0.61 L per mol.
    Growth and yield are not modelled beyond that."""

    DAY = 24.66
    SOLAR_PEAK = 20000.0
    CROP_POWER_NEED = 15200.0   # Shelf asks 1520 W/m2 x 10 m2
    TOTAL_MOLES = 2050.0
    CYCLE = 672                 # lettuce maturity, ticks
    CO2_PEAK = 0.30             # mol/tick at maturity
    TRANSP_PER_CO2 = 1.35       # L/mol
    INCORP_PER_CO2 = 0.61       # L/mol
    RESERVOIR_L = 500.0         # 50 L/m2 x 10 m2
    VAPOR_MOL = 40.0            # held there by the dehumidifier at RH 0.7
    RECYCLER_W_PER_L = 1540.0 / 4.26
    RECYCLER_MAX_L = 10.0

    _SOLAR_FACTOR = {"LOW_MALF": 0.9, "MEDIUM_MALF": 0.75, "SEVERE_MALF": 0.5}

    def __init__(self):
        self.reset()

    def reset(self) -> Dict[str, Any]:
        self.tick_n = 0
        self.power = 100000.0
        self.potable = 1000.0
        self.grey = 2000.0
        self.dirty = 0.0
        self.reservoir = 0.0
        self.co2_store = 200.0
        self.co2_gh = 0.0012 * self.TOTAL_MOLES
        self.biomass = 0.0
        self.age_h = 0
        self.water_ok_ticks = 0
        self.solar_scale = 1.0
        self.recycler_broken = False
        self.cmd = {"cmd.crop_water_L": 20.0, "cmd.crop_power_W": 2000.0,
                    "cmd.co2_inject_mol": 0.0, "cmd.recycler_power_W": 0.0}
        self.last = {"solar": 0.0, "crop_power": 0.0, "crop_water": 0.0, "co2_in": 0.0, "co2_up": 0.0,
                     "transp": 0.0, "recycled": 0.0, "recycler_power": 0.0}
        self.faults: list = []
        return self.read()

    def read(self) -> Dict[str, Any]:
        hour = self.tick_n % self.DAY
        s = {
            "time.tick": self.tick_n,
            "time.hour_of_day": hour,
            "gh.co2_ppm": self.co2_gh / self.TOTAL_MOLES * 1e6,
            "gh.o2_frac": 0.21,
            "gh.rh": 0.7,
            "gh.pressure_kPa": 101.0,
            "gh.light": 50000 * math.sin(math.pi / self.DAY * hour) ** 2,
            "gh.vapor_mol": self.VAPOR_MOL,
            "power.store_level": self.power,
            "power.store_capacity": 200000.0,
            "power.solar_out": self.last["solar"],
            "power.crop_draw": self.last["crop_power"],
            "power.recycler_draw": self.last["recycler_power"],
            "water.potable_L": self.potable,
            "water.grey_L": self.grey,
            "water.dirty_L": self.dirty,
            "water.crop_draw_L": self.last["crop_water"],
            "water.dehumidified_L": self.last["transp"],
            "water.recycled_L": self.last["recycled"],
            "co2.store_mol": self.co2_store,
            "co2.injected_mol": self.last["co2_in"],
            "crop.biomass_store_kg": self.biomass,
            "sim.ended": False,
            "faults.active": list(self.faults),
            shelf_key(0, "time_till_canopy_closure"): max(0.0, 20 - self.age_h / 24),
            shelf_key(0, "harvest_interval"): 28.0,
            shelf_key(0, "co2_inhaled_mol"): self.last["co2_up"],
            shelf_key(0, "ppf_needed"): 295.14,
            shelf_key(0, "reservoir_capacity_L"): self.RESERVOIR_L,
            shelf_key(0, "reservoir_L"): self.reservoir,
        }
        return s

    def apply(self, commands: Dict[str, float]) -> None:
        _check_commands(commands)
        self.cmd.update({k: float(v) for k, v in commands.items()})

    def step(self) -> Dict[str, Any]:
        hour = self.tick_n % self.DAY
        solar = self.SOLAR_PEAK * self.solar_scale * math.sin(math.pi / self.DAY * hour) ** 2
        crop_power = min(self.cmd["cmd.crop_power_W"], self.CROP_POWER_NEED, self.power + solar)
        self.power = min(200000.0, self.power + solar - crop_power)

        # recycler: draws all the power it is given, treats dirty water in proportion
        rec_power = 0.0 if self.recycler_broken else min(self.cmd["cmd.recycler_power_W"], 1540.0, self.power)
        self.power -= rec_power
        recycled = min(self.dirty, rec_power / self.RECYCLER_W_PER_L, self.RECYCLER_MAX_L)
        self.dirty -= recycled
        self.potable += recycled

        # shelf refills its reservoir, then the crop takes its uptake from it
        gathered = min(self.RESERVOIR_L - self.reservoir, self.cmd["cmd.crop_water_L"], self.potable)
        self.potable -= gathered
        self.reservoir += gathered
        co2_need = self.CO2_PEAK * min(1.0, self.age_h / self.CYCLE) ** 2.5 if crop_power > 0 else 0.0
        co2_need = min(co2_need, self.co2_gh)  # toy simplification: no CO2, no activity
        need = (self.TRANSP_PER_CO2 + self.INCORP_PER_CO2) * co2_need
        uptake = min(need, self.reservoir)
        frac = uptake / need if need > 0 else 1.0
        self.reservoir -= uptake
        co2_up = frac * co2_need
        transp = frac * self.TRANSP_PER_CO2 * co2_need
        self.dirty += transp  # dehumidifier condenses all of it, vapor stays put

        co2_in = min(self.cmd["cmd.co2_inject_mol"], 5.0, self.co2_store)
        self.co2_store -= co2_in
        self.co2_gh += co2_in - co2_up

        self.age_h += 1
        self.water_ok_ticks += frac >= 0.999
        if self.age_h >= self.CYCLE:
            self.biomass += 35.5 * self.water_ok_ticks / self.CYCLE
            self.age_h = 0
            self.water_ok_ticks = 0
        self.last = {"solar": solar, "crop_power": crop_power, "crop_water": gathered, "co2_in": co2_in,
                     "co2_up": co2_up, "transp": transp, "recycled": recycled, "recycler_power": rec_power}
        self.tick_n += 1
        return self.read()

    def inject(self, fault: Fault) -> None:
        if fault.kind != "malfunction":
            return
        intensity = Intensity(fault.params.get("intensity", Intensity.MEDIUM)).value
        if fault.target == bindings.SOLAR:
            self.solar_scale *= self._SOLAR_FACTOR[intensity]
        if fault.target == bindings.RECYCLER:
            # BioSim zeroes the recycler's max flow rates and clearing never restores them
            self.recycler_broken = True
        self.faults.append({"module": fault.target, "id": id(fault), "intensity": intensity,
                            "length": Length(fault.params.get("length", Length.TEMPORARY)).value,
                            "tick": self.tick_n})

    def clear(self, fault: Fault) -> None:
        if fault.kind != "malfunction":
            return
        remaining = [f for f in self.faults if f["id"] != id(fault)]
        if len(remaining) == len(self.faults):
            return
        self.faults = remaining
        if fault.target == bindings.SOLAR:
            self.solar_scale /= self._SOLAR_FACTOR[Intensity(fault.params.get("intensity", Intensity.MEDIUM)).value]

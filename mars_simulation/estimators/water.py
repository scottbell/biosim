"""Estimates the lettuce bed's hidden reservoir level from data a controller can see.

What BioSim does each tick (Shelf.tick, Plant.growBiomass), for one shelf:
    gather   g = min(R - x_prev, cap, tank)      -> reported as water.crop_draw_L
    uptake   u = transpired + incorporated        -> taken from the reservoir
    level    x = x_prev + g - u                   -> hidden (truth-only crop.s0.reservoir_L)

What we can see:
    transpired: the vapor balance of the greenhouse air,
        (gh.vapor_mol now - before + dehumidified moles) * 0.018 L/mol
    incorporated: water built into new biomass, measured at r = 0.61 L per mol of CO2 uptake
        (crop.s0.co2_inhaled_mol), constant over a whole cycle in BioSim
    exact level: when the draw is below the cap and the tank isn't empty, the shelf refilled
        to full, so x_prev = R - draw exactly. With a low reservoir the cap always binds and
        this never happens, so the estimate rides on the uptake model alone.

Assumes nothing else adds or removes greenhouse vapor (no crew, no leaks) and one shelf.
"""
from __future__ import annotations

from typing import Any, Dict, Optional

from interface.bindings import L_PER_MOL_WATER

INCORPORATED_L_PER_MOL_CO2 = 0.61   # measured on BioSim lettuce, 2026-10-30 (plant_next_steps.md)


class ReservoirEstimator:
    def __init__(self, incorporated_per_co2: float = INCORPORATED_L_PER_MOL_CO2,
                 adapt_rate: float = 0.05, shelf: int = 0, initial_level_L: float = 0.0,
                 exact_tolerance_L: float = 5.0):
        self.r0 = float(incorporated_per_co2)
        self.adapt_rate = float(adapt_rate)
        self.exact_tolerance_L = float(exact_tolerance_L)
        self.prefix = f"crop.s{shelf}."
        self.initial_level = float(initial_level_L)
        self.reset()

    def reset(self) -> None:
        self.level = self.initial_level
        self.r = self.r0
        self._prev_vapor: Optional[float] = None
        self._prev_transp: Optional[float] = None
        self._prev_co2: Optional[float] = None
        self._prev_exact = False
        self._last_uptake = 0.0

    def update(self, obs: Dict[str, Any], cap_L: float) -> Dict[str, Any]:
        """obs: the state after a step. cap_L: the crop water command that was in force
        during that step (the controller's previous command)."""
        vapor = obs.get("gh.vapor_mol")
        if self._prev_vapor is None:
            # first call, before any step: nothing moved yet
            self._prev_vapor = vapor
            return self._report(transp=0.0, uptake=0.0, exact=False, degraded=vapor is None)

        R = obs.get(self.prefix + "reservoir_capacity_L")
        draw = obs.get("water.crop_draw_L")
        tank = obs.get("water.potable_L")
        dehum = obs.get("water.dehumidified_L")
        co2 = obs.get(self.prefix + "co2_inhaled_mol")
        degraded = any(v is None for v in (R, draw, tank, dehum, co2, vapor)) or self._prev_vapor is None

        if vapor is not None and dehum is not None and self._prev_vapor is not None:
            transp = max(0.0, (vapor - self._prev_vapor) * L_PER_MOL_WATER + dehum)
        else:
            transp = None
        uptake = (transp + self.r * co2) if (transp is not None and co2 is not None) else self._last_uptake

        if draw is None:
            draw = cap_L  # missing reading: assume the command was carried out
        exact = (not degraded) and draw < cap_L - 1e-6 and tank > 1e-6
        if exact and abs((R - draw) - self.level) > max(self.exact_tolerance_L, 0.1 * R):
            # A draw under the cap also happens when something downstream (supervisor, stuck
            # valve) lowered the command. Only trust "refilled to full" if it agrees with us.
            exact = False

        if exact:
            # refill reached full this step; previous level was R - draw
            if self._prev_exact and self._prev_transp is not None and self._prev_co2 and self._prev_co2 > 1e-4:
                # two full refills in a row: last step's uptake was exactly this draw
                r_obs = (draw - self._prev_transp) / self._prev_co2
                self.r += self.adapt_rate * (r_obs - self.r)
            level_pre = R
        else:
            level_pre = self.level + draw if R is None else min(R, self.level + draw)
        self.level = max(0.0, level_pre - uptake)

        self._prev_vapor = vapor
        self._prev_transp = transp
        self._prev_co2 = co2
        self._prev_exact = exact
        self._last_uptake = uptake
        return self._report(transp=transp or 0.0, uptake=uptake, exact=exact, degraded=degraded,
                            dry=level_pre - uptake < 0)

    def _report(self, transp: float, uptake: float, exact: bool, degraded: bool, dry: bool = False):
        return {
            "est.reservoir_L": self.level,
            "est.uptake_L": uptake,
            "est.transpired_L": transp,
            "est.incorporated_per_co2": self.r,
            "est.exact": exact,
            "est.dry": dry,
            "est.degraded": degraded,
        }

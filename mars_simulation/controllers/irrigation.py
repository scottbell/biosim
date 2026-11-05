"""Irrigation and water recycling controller for the lettuce bed.

Why a small buffer and not a full reservoir: BioSim refills the shelf's 500 L reservoir from
the tank, but the crop only uses about 140 L per cycle. A full reservoir locks ~500 L away from
the tank; measured yield was the same (35.5 kg) whether the bed could draw 20 L/tick or
0.2 L/tick, and fell to 19.6 kg at 0.1 L/tick and to 0 (crop died) at 0.05 L/tick.

Loop:  estimate reservoir x_hat and uptake u_hat (ReservoirEstimator)
       target  = max(min_buffer_L, buffer_hours * u_avg)
       command = clip(u_avg + kp * (target - x_hat), 0, max_cap_L)
When the tank is low the target drops to min_buffer_L so water stays in the tank.
The recycler is run just hard enough to treat the dirty water that is waiting.
"""
from __future__ import annotations

from typing import Any, Dict, Optional

from estimators import ReservoirEstimator

RECYCLER_W_PER_L = 1540.0 / 4.26   # WaterRSLinear: 1540 W treats 4.26 L per tick
RECYCLER_MAX_W = 1540.0


class IrrigationController:
    def __init__(self, buffer_hours: float = 24.0, min_buffer_L: float = 5.0, kp: float = 0.1,
                 max_cap_L: float = 200.0, tank_reserve_L: float = 100.0, uptake_smoothing: float = 0.1,
                 run_recycler: bool = True, estimator: Optional[ReservoirEstimator] = None):
        self.buffer_hours = float(buffer_hours)
        self.min_buffer_L = float(min_buffer_L)
        self.kp = float(kp)
        self.max_cap_L = float(max_cap_L)
        self.tank_reserve_L = float(tank_reserve_L)
        self.alpha = float(uptake_smoothing)
        self.run_recycler = run_recycler
        self.estimator = estimator or ReservoirEstimator()
        self.reset()

    def reset(self) -> None:
        self.estimator.reset()
        self.u_avg = 0.0
        self.last_cap = 0.0
        self.last_recycler_W = 0.0
        self.last_estimate: Dict[str, Any] = {}

    def __call__(self, obs: Dict[str, Any]) -> Dict[str, float]:
        est = self.estimator.update(obs, self.last_cap)
        self.last_estimate = est
        self.u_avg += self.alpha * (est["est.uptake_L"] - self.u_avg)

        target = max(self.min_buffer_L, self.buffer_hours * self.u_avg)
        tank = obs.get("water.potable_L")
        if tank is not None and tank < self.tank_reserve_L:
            target = self.min_buffer_L
        cap = self.u_avg + self.kp * (target - est["est.reservoir_L"])
        cap = min(self.max_cap_L, max(0.0, cap))

        cmds = {"cmd.crop_water_L": cap}
        if self.run_recycler:
            dirty = obs.get("water.dirty_L")
            # missing reading: keep the last setting rather than guess
            recycler = self.last_recycler_W if dirty is None else min(RECYCLER_MAX_W, dirty * RECYCLER_W_PER_L)
            cmds["cmd.recycler_power_W"] = recycler
            self.last_recycler_W = recycler

        self.last_cap = cap
        return cmds

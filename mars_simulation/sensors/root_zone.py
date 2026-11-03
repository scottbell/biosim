"""Simulated level sensor for the lettuce bed's reservoir (the root zone).

BioSim is hydroponic, so "soil moisture" is the water in the shelf reservoir. The true level
is truth-only (crop.s0.reservoir_L); this turns it into what a real level sensor would report.
It takes the true level as an argument, so it works however the loop ends up giving it one
(see root_zone_sensor.md, section 2).

    reading = clip(true + bias + drift + noise, 0, capacity)    then stuck / dropout override

Faults use the shared Fault record with target=KEY:
    noise    extra Gaussian noise          params: sigma (L)
    bias     constant offset               params: offset (L)
    drift    offset growing every tick     params: rate (L/tick), counted from fault.tick
    stuck    repeats the last reading      (the reading before the fault, if there was one)
    dropout  no reading (None)
"""
from __future__ import annotations

import random
from typing import Iterable, Optional

from interface.schema import Fault

KEY = "crop.s0.root_zone_L"
FAULT_KINDS = ("noise", "bias", "drift", "stuck", "dropout")


class RootZoneSensor:
    def __init__(self, capacity_L: float = 500.0, sigma_L: float = 0.1, seed: int = 0):
        if sigma_L < 0:
            raise ValueError("sigma_L must be nonnegative")
        self.capacity_L = float(capacity_L)
        self.sigma_L = float(sigma_L)
        self.seed = seed
        self.reset()

    def reset(self) -> None:
        self.rng = random.Random(self.seed)
        self.last: Optional[float] = None   # last reading actually reported
        self._held: Optional[float] = None  # value a stuck sensor keeps repeating

    def read(self, true_L: float, tick: int, active_faults: Iterable[Fault] = ()) -> Optional[float]:
        mine = [f for f in active_faults if f.target == KEY]
        for f in mine:
            if f.kind not in FAULT_KINDS:
                raise ValueError(f"unknown root-zone sensor fault {f.kind!r}; valid: {FAULT_KINDS}")
        kinds = {f.kind for f in mine}

        sigma2 = self.sigma_L ** 2
        value = float(true_L)
        for f in mine:
            if f.kind == "bias":
                value += float(f.params.get("offset", 0.0))
            elif f.kind == "drift":
                value += float(f.params.get("rate", 0.0)) * (tick - f.tick + 1)
            elif f.kind == "noise":
                sigma = float(f.params.get("sigma", 0.0))
                if sigma < 0:
                    raise ValueError("noise sigma must be nonnegative")
                sigma2 += sigma ** 2
        value += self.rng.gauss(0.0, sigma2 ** 0.5)  # drawn every tick so runs stay repeatable
        value = min(self.capacity_L, max(0.0, value))

        if "stuck" in kinds:
            if self._held is None:
                self._held = self.last if self.last is not None else value
            value = self._held
        else:
            self._held = None

        if "dropout" in kinds:
            return None
        self.last = value
        return value

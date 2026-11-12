"""Atmosphere and temperature sensor fault modeling for Martian greenhouse CPS.

Provides realistic sensor imperfection and fault injection matching the project's
Fault schema (target, kind, params, duration):
- noise:   additional Gaussian measurement noise (sigma)
- bias:    constant measurement offset (offset)
- drift:   linearly growing offset (rate per tick)
- stuck:   output frozen at previous pre-fault measurement
- dropout: measurement unavailable (None)

Sensor physics applied before faults (these used to live in HybridPlant):
- temperature probe lag: first order, time constant temp_tau_hours
- NDIR CO2 cross-sensitivity: co2_temp_cross_sensitivity ppm per °C away from 22 °C

Targets:
    gh.temp_C        Cabin sensed temperature (°C)
    gh.co2_ppm       NDIR carbon dioxide concentration (ppm)
    gh.o2_frac       Oxygen mole fraction (-)
    gh.pressure_kPa  Cabin total pressure (kPa)
"""
from __future__ import annotations

import math
import random
from typing import Any, Callable, Dict, Iterable, List, Optional, Set

from interface.loop import State, TickContext, SensorLayer
from interface.schema import Fault
from interface.sensors import temp_alarm_band

ATMOSPHERE_SENSOR_KEYS = frozenset({
    "gh.temp_C",
    "gh.co2_ppm",
    "gh.o2_frac",
    "gh.pressure_kPa",
})

SUPPORTED_FAULT_KINDS = frozenset({"noise", "bias", "drift", "stuck", "dropout"})


class AtmosphereChannelSensor:
    """Individual sensor channel with baseline noise and runtime fault injection."""

    def __init__(
        self,
        key: str,
        baseline_sigma: float = 0.0,
        min_val: Optional[float] = None,
        max_val: Optional[float] = None,
        seed: Optional[int] = None,
    ):
        self.key = key
        self.baseline_sigma = max(0.0, float(baseline_sigma))
        self.min_val = min_val
        self.max_val = max_val
        self.rng = random.Random(seed)
        self.reset()

    def reset(self) -> None:
        self.last_reported: Optional[float] = None
        self._held_value: Optional[float] = None

    def read(
        self,
        true_value: float,
        tick: int,
        active_faults: Iterable[Fault] = (),
    ) -> Optional[float]:
        """Applies baseline noise and active faults to true_value."""
        if true_value is None:
            return None

        mine = [f for f in active_faults if f.target == self.key and f.active_at(tick)]
        for f in mine:
            if f.kind not in SUPPORTED_FAULT_KINDS:
                raise ValueError(
                    f"Unknown fault kind {f.kind!r} on {self.key}; supported: {sorted(SUPPORTED_FAULT_KINDS)}"
                )

        kinds: Set[str] = {f.kind for f in mine}

        # Dropout takes precedence
        if "dropout" in kinds:
            return None

        # Stuck behavior
        if "stuck" in kinds:
            if self._held_value is None:
                self._held_value = (
                    self.last_reported if self.last_reported is not None else float(true_value)
                )
            return self._held_value
        else:
            self._held_value = None

        # Compute bias, drift, and noise variance
        val = float(true_value)
        total_var = self.baseline_sigma ** 2

        for f in mine:
            if f.kind == "bias":
                val += float(f.params.get("offset", 0.0))
            elif f.kind == "drift":
                rate = float(f.params.get("rate", 0.0))
                elapsed_ticks = max(1, tick - f.tick + 1)
                val += rate * elapsed_ticks
            elif f.kind == "noise":
                sigma = float(f.params.get("sigma", 0.0))
                if sigma < 0.0:
                    raise ValueError(f"noise sigma for {self.key} must be nonnegative")
                total_var += sigma ** 2

        if total_var > 0.0:
            val += self.rng.gauss(0.0, math.sqrt(total_var))

        # Clamp to physical range
        if self.min_val is not None:
            val = max(self.min_val, val)
        if self.max_val is not None:
            val = min(self.max_val, val)

        self.last_reported = val
        return val


class AtmosphereSensorLayer:
    """Sensor layer callable compatible with interface.loop.run().

    Simulates imperfect sensed observations for cabin temperature, CO2, O2,
    and pressure while preserving ground-truth values untouched.
    """

    def __init__(
        self,
        temp_sigma_C: float = 0.15,
        co2_sigma_ppm: float = 5.0,
        o2_sigma_frac: float = 0.0005,
        pressure_sigma_kPa: float = 0.1,
        seed: Optional[int] = 42,
        temp_tau_hours: float = 0.5,
        co2_temp_cross_sensitivity: float = 5.0,
        calibration_temp_C: float = 22.0,
    ):
        base_seed = seed if seed is not None else 42
        self.temp_tau_hours = float(temp_tau_hours)
        self.co2_temp_cross_sensitivity = float(co2_temp_cross_sensitivity)
        self.calibration_temp_C = float(calibration_temp_C)
        self.channels: Dict[str, AtmosphereChannelSensor] = {
            "gh.temp_C": AtmosphereChannelSensor(
                "gh.temp_C",
                baseline_sigma=temp_sigma_C,
                min_val=-150.0,
                max_val=100.0,
                seed=base_seed + 1,
            ),
            "gh.co2_ppm": AtmosphereChannelSensor(
                "gh.co2_ppm",
                baseline_sigma=co2_sigma_ppm,
                min_val=0.0,
                max_val=50000.0,
                seed=base_seed + 2,
            ),
            "gh.o2_frac": AtmosphereChannelSensor(
                "gh.o2_frac",
                baseline_sigma=o2_sigma_frac,
                min_val=0.0,
                max_val=1.0,
                seed=base_seed + 3,
            ),
            "gh.pressure_kPa": AtmosphereChannelSensor(
                "gh.pressure_kPa",
                baseline_sigma=pressure_sigma_kPa,
                min_val=0.0,
                max_val=200.0,
                seed=base_seed + 4,
            ),
        }
        self.reset()

    def reset(self) -> None:
        for ch in self.channels.values():
            ch.reset()
        self._lag_temp: Optional[float] = None

    def _physics(self, truth: State) -> Dict[str, float]:
        """What a healthy sensor would read: true values with lag and cross-sensitivity."""
        raw = {k: float(truth[k]) for k in self.channels if truth.get(k) is not None}
        if "gh.temp_C" in raw:
            true_temp = raw["gh.temp_C"]
            if self._lag_temp is None:
                self._lag_temp = true_temp
            alpha = 1.0 - math.exp(-1.0 / max(1e-3, self.temp_tau_hours))
            self._lag_temp += alpha * (true_temp - self._lag_temp)
            raw["gh.temp_C"] = self._lag_temp
            if "gh.co2_ppm" in raw:
                raw["gh.co2_ppm"] += self.co2_temp_cross_sensitivity * (true_temp - self.calibration_temp_C)
        return raw

    def __call__(self, truth: State, ctx: TickContext) -> State:
        obs = dict(truth)
        for key, value in self._physics(truth).items():
            obs[key] = self.channels[key].read(value, tick=ctx.tick, active_faults=ctx.active_faults)

        sensed_temp = obs.get("gh.temp_C")
        obs["gh.temp_alarm"] = None if sensed_temp is None else temp_alarm_band(sensed_temp)
        return obs


def chain_sensor_layers(*layers: SensorLayer) -> SensorLayer:
    """Chains multiple sensor layers in sequence: layer_N(...(layer_1(truth, ctx)))."""
    def chained(truth: State, ctx: TickContext) -> State:
        current = dict(truth)
        for layer in layers:
            current = layer(current, ctx)
        return current
    return chained

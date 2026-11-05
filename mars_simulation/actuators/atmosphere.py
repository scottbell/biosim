"""Atmosphere and temperature actuator fault modeling for Martian greenhouse CPS.

Simulates actuator imperfections and hardware malfunctions on control outputs:
- cmd.heater_W:       Greenhouse electric heater (burnout, stuck relay, reduced efficiency)
- cmd.co2_inject_mol: CO2 injector valve (stuck closed, clogged nozzle, stuck open)
- cmd.isru_co2_mol:   Mars atmosphere ISRU compressor (compressor seizure, degradation)

Supported actuator fault kinds:
- stuck:       actuator command clamped to a constant value (e.g. value=0 for burnout)
- efficiency:  attenuates command by multiplicative factor in [0, 1]
- bias:        additive bias to commanded actuation
- dropout:     actuation drops to 0.0
"""
from __future__ import annotations

from typing import Dict, Iterable, Optional, Set

from interface.loop import ActuatorLayer, Commands, TickContext
from interface.schema import Fault

ATMOSPHERE_ACTUATOR_KEYS = frozenset({
    "cmd.heater_W",
    "cmd.co2_inject_mol",
    "cmd.isru_co2_mol",
})

SUPPORTED_ACTUATOR_FAULTS = frozenset({"stuck", "efficiency", "bias", "dropout"})


class AtmosphereActuatorLayer:
    """Actuator layer compatible with interface.loop.run(actuator_layer=...)."""

    def __init__(self, limits: Optional[Dict[str, float]] = None):
        self.limits = limits or {
            "cmd.heater_W": 5000.0,
            "cmd.co2_inject_mol": 5.0,
            "cmd.isru_co2_mol": 10.0,
        }

    def __call__(self, commands: Commands, ctx: TickContext) -> Commands:
        applied = dict(commands)
        active_faults = ctx.active_faults

        for key in ATMOSPHERE_ACTUATOR_KEYS:
            if key not in applied:
                continue

            mine = [f for f in active_faults if f.target == key and f.active_at(ctx.tick)]
            if not mine:
                continue

            for f in mine:
                if f.kind not in SUPPORTED_ACTUATOR_FAULTS:
                    raise ValueError(
                        f"Unknown actuator fault kind {f.kind!r} on {key}; supported: {sorted(SUPPORTED_ACTUATOR_FAULTS)}"
                    )

            kinds: Set[str] = {f.kind for f in mine}

            if "dropout" in kinds:
                applied[key] = 0.0
                continue

            if "stuck" in kinds:
                stuck_fault = next(f for f in mine if f.kind == "stuck")
                applied[key] = float(stuck_fault.params.get("value", 0.0))
                continue

            val = float(applied[key])
            for f in mine:
                if f.kind == "efficiency":
                    factor = float(f.params.get("factor", 1.0))
                    val *= max(0.0, min(1.0, factor))
                elif f.kind == "bias":
                    val += float(f.params.get("offset", 0.0))

            max_limit = self.limits.get(key, float("inf"))
            applied[key] = max(0.0, min(max_limit, val))

        return applied


def chain_actuator_layers(*layers: ActuatorLayer) -> ActuatorLayer:
    """Chains multiple actuator layers in sequence: layer_N(...(layer_1(commands, ctx)))."""
    def chained(commands: Commands, ctx: TickContext) -> Commands:
        current = dict(commands)
        for layer in layers:
            current = layer(current, ctx)
        return current
    return chained

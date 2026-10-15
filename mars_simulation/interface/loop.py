"""The per-tick pipeline. Every stage except the plant is a plain function with a
do-nothing default, so the loop runs end to end before any controller or fault layer exists.

    truth --strip truth-only--> sensor_layer --> obs --> controllers --> supervisor
          --> actuator_layer --> plant.apply --> plant.step --> next truth

Timing: commands computed from the state at tick k act during the step k -> k+1.
BioSim stores report last tick's values, so there is always one tick of actuation delay.
"""
from __future__ import annotations

import time
from dataclasses import dataclass, field
from typing import Any, Callable, Dict, Iterable, List, Optional

from .log import TickLogger
from .plant import Plant
from .schema import Fault, public

State = Dict[str, Any]
Commands = Dict[str, float]
Controller = Callable[[State], Commands]


@dataclass
class TickContext:
    tick: int
    active_faults: List[Fault] = field(default_factory=list)


SensorLayer = Callable[[State, TickContext], State]
Supervisor = Callable[[State, Dict[str, Commands], TickContext], Commands]
ActuatorLayer = Callable[[Commands, TickContext], Commands]


def pass_through_sensors(truth: State, ctx: TickContext) -> State:
    return dict(truth)


def merge_commands(obs: State, per_controller: Dict[str, Commands], ctx: TickContext) -> Commands:
    """Default supervisor: merge, and refuse if two controllers write the same key.
    A real supervisor (power arbitration) replaces this and sees who asked for what."""
    merged: Commands = {}
    owner: Dict[str, str] = {}
    for name, cmds in per_controller.items():
        for key, value in cmds.items():
            if key in merged:
                raise ValueError(f"{name} and {owner[key]} both command {key}; "
                                 "pass a supervisor that arbitrates")
            merged[key] = value
            owner[key] = name
    return merged


def pass_through_actuators(commands: Commands, ctx: TickContext) -> Commands:
    return dict(commands)


def run(plant: Plant,
        controllers: Dict[str, Controller],
        n_ticks: int,
        sensor_layer: SensorLayer = pass_through_sensors,
        supervisor: Supervisor = merge_commands,
        actuator_layer: ActuatorLayer = pass_through_actuators,
        fault_schedule: Iterable[Fault] = (),
        logger: Optional[TickLogger] = None,
        reset: bool = True) -> State:
    """Runs n_ticks and returns the final truth state. Stops early if BioSim ends the run."""
    logger = logger or TickLogger()
    faults = sorted(fault_schedule, key=lambda f: f.tick)
    truth = plant.reset() if reset else plant.read()
    started: List[Fault] = []

    for _ in range(n_ticks):
        t0 = time.perf_counter()
        tick = int(truth["time.tick"])

        # identity, not ==, so two identical faults scheduled together stay separate
        for f in faults:
            if not any(s is f for s in started) and f.active_at(tick):
                plant.inject(f)
                started.append(f)
        ended = [f for f in started if not f.active_at(tick)]
        for f in ended:
            plant.clear(f)
        started = [f for f in started if not any(f is e for e in ended)]
        ctx = TickContext(tick=tick, active_faults=list(started))

        obs = sensor_layer(public(truth), ctx)
        per_controller = {name: dict(c(obs)) for name, c in controllers.items()}
        commands = supervisor(obs, per_controller, ctx)
        applied = actuator_layer(commands, ctx)
        plant.apply(applied)
        next_truth = plant.step()

        logger.write({
            "tick": tick,
            "truth": truth,
            "obs": obs,
            "commands": per_controller,
            "applied": applied,
            "faults": [f.to_dict() for f in started],
            "wall_ms": (time.perf_counter() - t0) * 1000,
        })
        truth = next_truth
        if truth.get("sim.ended"):
            break
    return truth

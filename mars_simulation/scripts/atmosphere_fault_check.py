"""Scores atmosphere and temperature fault detection (AtmosphereTemperatureMonitor) and checks
that the cabin stays safe, one run per fault scenario, through the real run() loop.

    python scripts/atmosphere_fault_check.py                  # live server, HybridPlant(BioSimPlant)
    python scripts/atmosphere_fault_check.py --fake           # HybridPlant(FakePlant), no server
    python scripts/atmosphere_fault_check.py --ticks 100 --fault-tick 30 --fault-len 30

Pipeline per tick: truth -> AtmosphereSensorLayer (lag, noise, sensor faults) -> monitor
(adds fused_temp_C and fault flags to obs) -> HVAC controller and a CO2/lamp hold
-> AtmosphereActuatorLayer (actuator faults) -> plant. The HVAC controls on fused_temp_C, so a
sensor fault the monitor catches doesn't reach the heater. See estimators.MonitorInLoop.

A scenario passes when:
  - the fault is flagged while it is active (fault scenarios only),
  - nothing is flagged before the fault, or after the alarm clears,
  - the true cabin temperature stays inside --safe-min..--safe-max.
The default safe range, 7-32 °C, is the edge of the CRITICAL band in interface.sensors
(temp_alarm_band). Those bands are the code's own; they are not sourced lettuce limits.
Exits 1 if any scenario fails.
"""
from __future__ import annotations

import argparse
import math
import statistics
import sys
from pathlib import Path
from typing import Any, Dict, List

ROOT = Path(__file__).resolve().parent.parent
sys.path.insert(0, str(ROOT))

from actuators import AtmosphereActuatorLayer  # noqa: E402
from controllers import HVACTemperatureController  # noqa: E402
from estimators import AtmosphereTemperatureMonitor, MonitorInLoop  # noqa: E402
from interface import BioSimClient, BioSimPlant, FakePlant, Fault, TickLogger, run  # noqa: E402
from interface.plant import HybridPlant  # noqa: E402
from sensors import AtmosphereSensorLayer, chain_sensor_layers  # noqa: E402


def atmosphere(obs: Dict[str, Any]) -> Dict[str, float]:
    # CO2 held near 1200 ppm; without it the crop empties the greenhouse by ~tick 290
    co2 = obs.get("gh.co2_ppm")
    mol = 0.0 if co2 is None else (1200.0 - co2) * 1e-6 * 2050.0
    return {"cmd.crop_power_W": 200.0, "cmd.co2_inject_mol": max(0.0, min(5.0, mol))}


def run_scenario(make_plant, faults: List[Fault], n_ticks: int, seed: int = 42) -> List[Dict[str, Any]]:
    loop = MonitorInLoop(AtmosphereTemperatureMonitor(temp_sensor_sigma_C=0.15, temp_model_sigma_C=0.25,
                                                      temp_shift_C=1.0, temp_h_sigmas=4.0))
    sensors = AtmosphereSensorLayer(temp_sigma_C=0.15, co2_sigma_ppm=5.0, seed=seed)
    log = TickLogger()
    run(make_plant(), {"hvac": HVACTemperatureController(), "atmo": atmosphere}, n_ticks,
        sensor_layer=chain_sensor_layers(sensors, loop.sensor_layer),
        supervisor=loop.supervisor, actuator_layer=AtmosphereActuatorLayer(),
        fault_schedule=faults, logger=log)
    rows = []
    for rec in log.records:
        d = loop.diag[rec["tick"]]
        rows.append({
            "tick": rec["tick"],
            "true_temp": rec["truth"]["gh.temp_truth_C"],
            "sensed_temp": rec["obs"].get("gh.temp_C"),
            "fused_temp": d["fused_temp_C"],
            "flags": d["temp_faults"] + d["co2_faults"] + d["actuator_faults"],
        })
    return rows


def score(rows: List[Dict[str, Any]], start: int, end: int, has_fault: bool,
          safe_min: float, safe_max: float) -> Dict[str, Any]:
    flagged = [r["tick"] for r in rows if r["flags"]]
    caught = next((t for t in flagged if start <= t < end), None) if has_fault else None
    cleared = next((r["tick"] for r in rows if r["tick"] >= end and not r["flags"]), None) if has_fault else None
    after = [t for t in flagged if cleared is not None and t > cleared]
    before = [t for t in flagged if t < start] if has_fault else flagged
    true = [r["true_temp"] for r in rows]

    def rms(key):
        errs = [(r[key] - r["true_temp"]) ** 2 for r in rows if r[key] is not None]
        return math.sqrt(statistics.fmean(errs)) if errs else float("nan")

    problems = []
    if has_fault and caught is None:
        problems.append("missed")
    if before:
        problems.append(f"{len(before)} false alarm(s) from tick {before[0]}")
    if has_fault and cleared is None:
        problems.append("alarm never clears")
    if after:
        problems.append(f"{len(after)} alarm(s) after clearing, from tick {after[0]}")
    if min(true) < safe_min or max(true) > safe_max:
        problems.append(f"cabin left {safe_min:g}-{safe_max:g} °C")
    return {
        "delay": None if caught is None else caught - start,
        "before": len(before),
        "cleared": None if cleared is None else cleared - end,
        "after": len(after),
        "sensed_rms": rms("sensed_temp"),
        "fused_rms": rms("fused_temp"),
        "range": (min(true), max(true)),
        "problems": problems,
    }


def scenarios(start: int, length: int):
    def f(kind, target, **params):
        return [Fault(tick=start, kind=kind, target=target, params=params, duration=length)]
    return [
        ("nominal",                  []),
        ("temp dropout",             f("dropout", "gh.temp_C")),
        ("temp stuck",               f("stuck", "gh.temp_C")),
        ("temp bias +3.5 °C",        f("bias", "gh.temp_C", offset=3.5)),
        # a controller on the raw reading would hold it at 22 °C, driving the real cabin to ~34 °C
        ("temp bias -12 °C",         f("bias", "gh.temp_C", offset=-12.0)),
        ("temp drift +0.15 °C/tick", f("drift", "gh.temp_C", rate=0.15)),
        ("heater burnout",           f("stuck", "cmd.heater_W", value=0.0)),
        ("CO2 bias +150 ppm",        f("bias", "gh.co2_ppm", offset=150.0)),
    ]


def main() -> int:
    ap = argparse.ArgumentParser()
    ap.add_argument("--url", default="http://localhost:8009")
    ap.add_argument("--fake", action="store_true", help="run against FakePlant (no server)")
    ap.add_argument("--ticks", type=int, default=100)
    ap.add_argument("--fault-tick", type=int, default=30)
    ap.add_argument("--fault-len", type=int, default=30)
    ap.add_argument("--safe-min", type=float, default=7.0, help="lowest safe true cabin temperature, °C")
    ap.add_argument("--safe-max", type=float, default=32.0, help="highest safe true cabin temperature, °C")
    args = ap.parse_args()

    if args.fake:
        def make_plant():
            return HybridPlant(FakePlant(), sync_biosim_temperature=False)
    else:
        client = BioSimClient(args.url)
        try:
            client.list_sims()
        except Exception as e:
            print(f"Cannot reach BioSim at {args.url}: {e}\nStart it with: docker compose up biosim-server")
            return 1

        def make_plant():
            return HybridPlant(BioSimPlant(client))

    start, end = args.fault_tick, args.fault_tick + args.fault_len
    print(f"{'FakePlant' if args.fake else 'BioSim'}, {args.ticks} ticks, faults from tick {start} "
          f"to {end - 1}, safe range {args.safe_min:g}-{args.safe_max:g} °C\n")
    print(f"{'scenario':26s} {'caught after':>12s} {'alarms before':>13s} {'clears after':>12s} "
          f"{'alarms after':>12s} {'sensed RMS':>10s} {'fused RMS':>9s} {'true temp':>13s}  result")
    failed = 0
    for name, faults in scenarios(start, args.fault_len):
        s = score(run_scenario(make_plant, faults, args.ticks), start, end, bool(faults),
                  args.safe_min, args.safe_max)
        delay = "-" if not faults else ("missed" if s["delay"] is None else f"{s['delay']} h")
        clears = "-" if not faults else ("never" if s["cleared"] is None else f"{s['cleared']} h")
        span = f"{s['range'][0]:.1f}..{s['range'][1]:.1f}"
        result = "PASS" if not s["problems"] else "FAIL: " + "; ".join(s["problems"])
        failed += bool(s["problems"])
        print(f"{name:26s} {delay:>12s} {s['before']:>13d} {clears:>12s} {s['after']:>12d} "
              f"{s['sensed_rms']:>8.2f} °C {s['fused_rms']:>6.2f} °C {span:>10s} °C  {result}")
    print(f"\n{failed} of {len(scenarios(start, args.fault_len))} scenarios failed.")
    return 1 if failed else 0


if __name__ == "__main__":
    sys.exit(main())

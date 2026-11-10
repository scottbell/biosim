"""Scores the root-zone sensor and RootZoneMonitor against BioSim's true reservoir level.

Runs one lettuce cycle with the irrigation controller, then replays the log through
ReservoirEstimator, RootZoneSensor and RootZoneMonitor once per fault scenario. Replaying
means the sensor reads the logged truth directly, so this works before the loop gives sensor
layers access to truth (root_zone_sensor.md, section 2). The sensor doesn't feed back into
irrigation yet, so one cycle serves every scenario.

    python scripts/root_zone_check.py                 # live server
    python scripts/root_zone_check.py --fake          # FakePlant, no server
    python scripts/root_zone_check.py --sigma 0.2 --fault-tick 300 --fault-len 120

CO2 is held near 1200 ppm and lamps at full light so the estimator's uptake model holds.
"""
from __future__ import annotations

import argparse
import statistics
import sys
from pathlib import Path
from typing import Any, Dict, Iterable, List, Optional

ROOT = Path(__file__).resolve().parent.parent
sys.path.insert(0, str(ROOT))

from controllers import IrrigationController  # noqa: E402
from estimators import ReservoirEstimator, RootZoneMonitor  # noqa: E402
from interface import BioSimClient, BioSimPlant, FakePlant, Fault, TickLogger, run  # noqa: E402
from sensors import ROOT_ZONE_KEY, RootZoneSensor  # noqa: E402

N_TICKS = 1000      # one 672-tick cycle plus 300+ ticks after the harvest
TRUE_KEY = "crop.s0.reservoir_L"


def atmosphere(obs: Dict[str, Any]) -> Dict[str, float]:
    mol = (1200.0 - obs["gh.co2_ppm"]) * 1e-6 * 2050.0
    return {"cmd.crop_power_W": 10.0, "cmd.co2_inject_mol": max(0.0, min(5.0, mol))}


def run_cycle(plant, ticks: int = N_TICKS) -> List[Dict[str, Any]]:
    log = TickLogger()
    run(plant, {"irrigation": IrrigationController(), "atmo": atmosphere}, ticks, logger=log)
    return log.records


def replay(records: List[Dict[str, Any]], faults: Iterable[Fault] = (), sigma_L: float = 0.1,
           degrade_from: Optional[int] = None, degrade_until: Optional[int] = None,
           seed: int = 0) -> List[Dict[str, Any]]:
    """Per-tick rows: monitor output plus true level, estimate and raw sensor reading.
    The estimator is marked degraded for degrade_from <= tick < degrade_until (simulates it
    breaking); degrade_until=None means until the end."""
    faults = list(faults)
    est, sensor, mon = ReservoirEstimator(), RootZoneSensor(sigma_L=sigma_L, seed=seed), RootZoneMonitor(sigma_L)
    cap, rows = 0.0, []
    for rec in records:
        e = est.update(rec["obs"], cap)
        cap = rec["applied"]["cmd.crop_water_L"]
        if degrade_from is not None and degrade_from <= rec["tick"] < (degrade_until or float("inf")):
            e = dict(e, **{"est.degraded": True})
        t = rec["tick"]
        reading = sensor.read(rec["truth"][TRUE_KEY], t, [f for f in faults if f.active_at(t)])
        out = mon.update(reading, e, rec["obs"]["gh.co2_ppm"], rec["obs"]["crop.biomass_store_kg"])
        rows.append(dict(out, tick=t, true_L=rec["truth"][TRUE_KEY],
                         est_L=e["est.reservoir_L"], sensor_L=reading))
    return rows


def rms(rows: List[Dict[str, Any]], key: str) -> float:
    errs = [(r[key] - r["true_L"]) ** 2 for r in rows if r[key] is not None]
    return statistics.fmean(errs) ** 0.5 if errs else float("nan")


def score(rows: List[Dict[str, Any]], label: Optional[str], start: int, end: int) -> Dict[str, Any]:
    alarmed = [r["tick"] for r in rows if r["rz.faults"]]
    caught = next((r["tick"] for r in rows if r["tick"] >= start and label in r["rz.faults"]), None) \
        if label else None
    during = [r for r in rows if start <= r["tick"] < end]
    cleared = next((r["tick"] for r in rows if r["tick"] >= end and not r["rz.faults"]), None)
    return {
        "delay": None if caught is None else caught - start,
        "before": sum(t < start for t in alarmed),
        "cleared": None if label is None else (None if cleared is None else cleared - end),
        "worst": max((abs(r["rz.level_L"] - r["true_L"]) for r in during), default=0.0),
    }


def scenarios(start: int, length: int):
    def f(kind, **params):
        return [Fault(tick=start, kind=kind, target=ROOT_ZONE_KEY, params=params, duration=length)]
    return [
        ("nominal",              None,        [],                       None),
        ("bias -2 L",            "offset",    f("bias", offset=-2.0),   None),
        ("bias +3 L",            "offset",    f("bias", offset=3.0),    None),
        ("drift +0.05 L/tick",   "offset",    f("drift", rate=0.05),    None),
        ("stuck",                "stuck",     f("stuck"),               None),
        ("dropout",              "dropout",   f("dropout"),             None),
        ("estimator degraded",   "estimator", [],                       start),
    ]


def main() -> int:
    ap = argparse.ArgumentParser()
    ap.add_argument("--url", default="http://localhost:8009")
    ap.add_argument("--fake", action="store_true", help="run against FakePlant (no server)")
    ap.add_argument("--sigma", type=float, default=0.1, help="sensor baseline noise, L")
    ap.add_argument("--fault-tick", type=int, default=300)
    ap.add_argument("--fault-len", type=int, default=120)
    ap.add_argument("--ticks", type=int, default=N_TICKS)
    args = ap.parse_args()

    if args.fake:
        plant = FakePlant()
    else:
        client = BioSimClient(args.url)
        try:
            client.list_sims()
        except Exception as e:
            print(f"Cannot reach BioSim at {args.url}: {e}\nStart it with: docker compose up biosim-server")
            return 1
        plant = BioSimPlant(client)

    records = run_cycle(plant, args.ticks)
    start, end = args.fault_tick, args.fault_tick + args.fault_len

    nominal = replay(records, sigma_L=args.sigma)[30:]
    print(f"Sensor sigma {args.sigma} L. Level error (RMS) over ticks 30-{args.ticks - 1}: estimate "
          f"{rms(nominal, 'est_L'):.3f} L, sensor {rms(nominal, 'sensor_L'):.3f} L, "
          f"combined {rms(nominal, 'rz.level_L'):.3f} L")
    print(f"Faults run from tick {start} to {end - 1}.\n")
    print(f"{'scenario':22s} {'caught after':>12s} {'alarms before':>14s} {'clears after':>13s} "
          f"{'worst error':>12s}  alarm ticks outside the fault")
    for name, label, faults, degrade in scenarios(start, args.fault_len):
        rows = replay(records, faults, args.sigma, degrade_from=degrade, degrade_until=end)
        s = score(rows, label, start, end)
        outside = [r["tick"] for r in rows if r["rz.faults"] and not start <= r["tick"] < end
                   and not (label and s["cleared"] is not None and end <= r["tick"] < end + s["cleared"])]
        delay = "-" if label is None else ("missed" if s["delay"] is None else f"{s['delay']} h")
        clears = "-" if s["cleared"] is None else f"{s['cleared']} h"
        span = f"{outside[0]}-{outside[-1]} ({len(outside)})" if outside else "none"
        print(f"{name:22s} {delay:>12s} {s['before']:>14d} {clears:>13s} {s['worst']:>10.2f} L  {span}")
    return 0


if __name__ == "__main__":
    sys.exit(main())

"""Runs one lettuce cycle on a live (patched) BioSim with the irrigation controller and scores
the reservoir estimator against BioSim's true reservoir level.

    python scripts/water_check.py                       # irrigation controller
    python scripts/water_check.py --fixed-cap 20        # old behaviour: fixed cap, reservoir kept full
    python scripts/water_check.py --recycler-fault 300 120   # recycler broken for ticks 300-419

CO2 is held near 1200 ppm and lamps at full light so water is the only thing that varies.
"""
from __future__ import annotations

import argparse
import math
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
sys.path.insert(0, str(ROOT))

from controllers import IrrigationController  # noqa: E402
from interface import BioSimClient, BioSimPlant, Fault, TickLogger, run  # noqa: E402
from interface import bindings  # noqa: E402


def co2_and_lamps(obs):
    return {"cmd.co2_inject_mol": 0.6 if obs["gh.co2_ppm"] < 1200 else 0.0, "cmd.crop_power_W": 10.0}


def main() -> int:
    ap = argparse.ArgumentParser()
    ap.add_argument("--url", default="http://localhost:8009")
    ap.add_argument("--ticks", type=int, default=700)
    ap.add_argument("--fixed-cap", type=float, default=None)
    ap.add_argument("--recycler-fault", type=int, nargs=2, metavar=("START", "LENGTH"))
    ap.add_argument("--log", type=Path, default=None)
    args = ap.parse_args()

    if args.fixed_cap is None:
        irrigation = IrrigationController()
    else:
        irrigation = lambda obs: {"cmd.crop_water_L": args.fixed_cap, "cmd.recycler_power_W":
                                  min(1540.0, (obs["water.dirty_L"] or 0) * 1540 / 4.26)}
    faults = []
    if args.recycler_fault:
        faults.append(Fault(tick=args.recycler_fault[0], kind="malfunction", target=bindings.RECYCLER,
                            params={"intensity": "SEVERE_MALF", "length": "PERMANENT_MALF"},
                            duration=args.recycler_fault[1]))

    log = TickLogger(args.log, keep_in_memory=True)
    final = run(BioSimPlant(BioSimClient(args.url)), {"irrigation": irrigation, "atmo": co2_and_lamps},
                args.ticks, fault_schedule=faults, logger=log)
    log.close()

    recs = log.records
    true_res = [r["truth"]["crop.s0.reservoir_L"] for r in recs]
    drawn = sum(r["truth"]["water.crop_draw_L"] for r in recs)
    print(f"harvest            {final['crop.biomass_store_kg']:.2f} kg")
    print(f"water drawn        {drawn:.1f} L into the reservoir")
    print(f"reservoir (true)   min {min(true_res[30:]):.2f} L after tick 30, end {true_res[-1]:.2f} L")
    print(f"potable tank       min {min(r['truth']['water.potable_L'] for r in recs):.1f} L, "
          f"end {final['water.potable_L']:.1f} L")
    print(f"recycled           {sum(r['truth']['water.recycled_L'] for r in recs):.1f} L, "
          f"dirty water left {final['water.dirty_L']:.1f} L")
    print(f"humidity           {min(r['truth']['gh.rh'] for r in recs):.3f} - {max(r['truth']['gh.rh'] for r in recs):.3f}")

    if args.fixed_cap is None:
        # estimate made at tick k (from obs k) describes the reservoir at the end of step k
        errs = []
        est_series = []
        est = IrrigationController().estimator
        cap = 0.0
        for r in recs:
            e = est.update(r["obs"], cap)
            cap = r["applied"].get("cmd.crop_water_L", cap)
            errs.append(e["est.reservoir_L"] - r["truth"]["crop.s0.reservoir_L"])
            est_series.append(e["est.reservoir_L"])
        rms = math.sqrt(sum(x * x for x in errs) / len(errs))
        worst = max(range(len(errs)), key=lambda i: abs(errs[i]))
        print(f"estimator error    rms {rms:.3f} L, worst {errs[worst]:+.3f} L at tick {recs[worst]['tick']}, "
              f"end {errs[-1]:+.3f} L, r = {est.r:.4f} L/mol")
    return 0


if __name__ == "__main__":
    sys.exit(main())

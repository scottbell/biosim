"""First check against a running BioSim server.

Starts configs/mars_lettuce.biosim, prints the state dict, changes the crop water command,
ticks, shows what moved, times the ticks, and saves the raw state as a test fixture.

    python scripts/smoke_test.py [--url http://localhost:8009] [--ticks 24]
"""
from __future__ import annotations

import argparse
import json
import sys
import time
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
sys.path.insert(0, str(ROOT))

from interface import BioSimClient, BioSimPlant  # noqa: E402
from interface import bindings  # noqa: E402

FIXTURE = ROOT / "tests" / "fixtures" / "state_sample.json"


def show(state, keys=None):
    for k in sorted(keys or state):
        print(f"  {k:36s} {state[k]}")


def main() -> int:
    ap = argparse.ArgumentParser()
    ap.add_argument("--url", default="http://localhost:8009")
    ap.add_argument("--ticks", type=int, default=24)
    args = ap.parse_args()

    client = BioSimClient(args.url)
    try:
        client.list_sims()
    except Exception as e:
        print(f"Cannot reach BioSim at {args.url}: {e}\nStart it with: docker compose up biosim-server")
        return 1

    plant = BioSimPlant(client)
    state = plant.reset()
    print(f"Started sim {client.sim_id}. Initial state:")
    show(state)

    FIXTURE.parent.mkdir(parents=True, exist_ok=True)
    FIXTURE.write_text(json.dumps(plant.raw, indent=1))
    print(f"\nSaved raw state to {FIXTURE}")

    before = state
    plant.apply({"cmd.crop_water_L": 5.0, "cmd.crop_power_W": 1000.0, "cmd.co2_inject_mol": 0.5})
    sent = bindings.find_definition(client.state(), bindings.COMMAND_BINDINGS["cmd.crop_water_L"][0])
    print(f"\nAfter command, Lettuce_Bed GreyWater desiredFlowRates = {sent['rates']['desiredFlowRates']}")

    t0 = time.perf_counter()
    for _ in range(args.ticks):
        state = plant.step()
    ms = (time.perf_counter() - t0) * 1000 / args.ticks
    print(f"\nAfter {args.ticks} ticks ({ms:.1f} ms per tick incl. state read):")
    changed = [k for k in state if k in before and state[k] != before[k]]
    for k in sorted(changed):
        print(f"  {k:36s} {before[k]} -> {state[k]}")
    print(f"\nProjected wall time for one lettuce cycle (672 ticks): {672 * ms / 1000:.0f} s")
    return 0


if __name__ == "__main__":
    sys.exit(main())

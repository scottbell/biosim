#!/usr/bin/env python3
"""Verify the EVA attach/detach fix on a live BioSim JVM (port 8019, simId=1)."""
import json
import subprocess
import sys

BASE = "http://127.0.0.1:8019/api/simulation/2"
GROUPS = ["Crew_Quarters_Group", "Galley_Group", "Labs_Group", "Maintenance_Group", "EVA_Crew_Group"]

def curl_json(url, method="GET"):
    args = ["curl", "-s", "-X", method, url]
    out = subprocess.check_output(args)
    return json.loads(out)

def get_crew_snapshot():
    """Return {group: [(name, activity) ...]} for all groups."""
    snap = {}
    for g in GROUPS:
        data = curl_json(f"{BASE}/modules/{g}")
        people = data.get("properties", {}).get("crewPeople", [])
        snap[g] = [(p["name"], p["currentActivity"]["name"]) for p in people]
    return snap

def tick():
    return curl_json(f"{BASE}/tick", method="POST")

def print_snapshot(t, snap):
    print(f"--- t={t} ---")
    all_names = []
    for g in GROUPS:
        for name, act in snap[g]:
            all_names.append(name)
            print(f"  {g}: {name} (activity={act})")
    print(f"  total crew visible: {len(all_names)} -> {sorted(all_names)}")
    sys.stdout.flush()

log = []
def record(t, snap):
    log.append((t, snap))
    print_snapshot(t, snap)

record(0, get_crew_snapshot())

for t in range(1, 31):
    tick()
    snap = get_crew_snapshot()
    record(t, snap)

print("\n=== SUMMARY ===")
for t, snap in log:
    names = sorted(n for g in GROUPS for n, a in snap[g])
    print(f"t={t}: {names}")

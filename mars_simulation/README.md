# mars_simulation

Python side of the Mars lettuce greenhouse project. BioSim (the Java server in this repo) is the plant. This folder holds the interface, configs, controllers and analysis. Nothing in here changes BioSim's Java code, and the Docker image doesn't include this folder.

## Setup

```bash
# from the repo root
docker compose up biosim-server            # BioSim on localhost:8009

cd mars_simulation
python3 -m venv .venv && source .venv/bin/activate
pip install -r requirements.txt

python scripts/smoke_test.py               # first check against the live server
pytest                                     # offline tests (no server needed)
pytest -m live                             # tests against the live server
```

## How to use it

```python
from interface import BioSimPlant, FakePlant, Fault, TickLogger, run

def irrigation(obs):                       # gets a state dict, returns a command dict
    return {"cmd.crop_water_L": 2.0 if obs["water.grey_L"] > 100 else 0.0}

storm = Fault(tick=200, kind="malfunction", target="Solar_Array",
              params={"intensity": "SEVERE_MALF", "length": "PERMANENT_MALF"}, duration=72)

with TickLogger("logs/run.jsonl") as log:
    run(BioSimPlant(), {"irrigation": irrigation}, n_ticks=672,
        fault_schedule=[storm], logger=log)
```

Use `FakePlant()` in place of `BioSimPlant()` to work without the server. It has the same keys and toy dynamics.

## Using the client directly

Most code should go through `BioSimPlant` and `run()`. Use `BioSimClient` for debugging or for anything the state dict doesn't cover. It returns raw BioSim JSON. To get the flat state dict, use `bindings.to_state(client.state())`.

```python
from pathlib import Path
from interface import BioSimClient, Intensity, Length

client = BioSimClient("http://localhost:8009")
client.start(Path("configs/mars_lettuce.biosim").read_text())   # returns sim id
client.tick()                                                   # returns tick count
bed = client.module("Lettuce_Bed")                              # raw JSON for one module
client.set_consumer("Lettuce_Bed", "GreyWater", [5.0])
mid = client.add_malfunction("Solar_Array", Intensity.SEVERE, Length.PERMANENT)
client.clear_malfunction("Solar_Array", mid)
```

| Method | What it does |
|--------|--------------|
| `list_sims()` | ids of all sims on the server |
| `start(xml)` | starts a new sim and returns its id. The client then uses that sim. |
| `attach(sim_id)` | points the client at an existing sim |
| `tick()` | advances one tick and returns the tick count |
| `state()` | full raw state: `{"globals": ..., "modules": ...}` |
| `globals()` | globals only (`ticksGoneBy`, `tickLength`, ...) |
| `module(name)` | one module's consumers, producers, properties and malfunctions |
| `set_consumer(module, type, [rates], connections=None)` | sets `desiredFlowRates` on a consumer |
| `set_producer(module, type, [rates], connections=None)` | same, for a producer |
| `add_malfunction(module, intensity, length, tick=None)` | starts a malfunction now and returns its id. With `tick`, schedules it instead and returns `None`. |
| `malfunctions(module)` | lists a module's malfunctions |
| `clear_malfunction(module, id=None)` | clears one malfunction, or all of them if no id is given |

Notes:
- **Spell `type` exactly as the state JSON does** (`GreyWater`, `PotableWater`, `Power`, `CO2`). Lowercase fails.
- **Only desired rates can change.** `maxFlowRates` is fixed by the XML.
- **Errors:** a failed request raises `BioSimError`, which carries `.status` and BioSim's `.message`.

## The data each tick

```
BioSim JSON --bindings--> truth --(truth-only keys removed)--> sensor_layer --> obs
obs --> controllers --> supervisor --> actuator_layer --> applied --> BioSim (REST) --> tick
every stage is logged: truth, obs, commands (per controller), applied, active faults
```

| Hook | Signature | Default |
|------|-----------|---------|
| controller | `f(obs) -> {cmd_key: value}` | none |
| `sensor_layer` | `f(truth, ctx) -> obs` | pass-through |
| `supervisor` | `f(obs, {controller_name: cmds}, ctx) -> cmds` | merge, error if two controllers set the same key |
| `actuator_layer` | `f(cmds, ctx) -> applied` | pass-through |

`ctx.active_faults` lists the faults active this tick:
- `kind="malfunction"` faults are applied by BioSim itself.
- Any other kind (`"noise"`, `"dropout"`, `"stuck"`, ...) only has an effect if a sensor or actuator layer looks for it.

All faults go into the log, so detection can be scored against them.

## Files

| File | What it holds |
|------|---------------|
| `interface/schema.py` | Every state and command key with units. Start here. |
| `interface/bindings.py` | The only place that knows BioSim module names. Change it when the config changes. |
| `interface/client.py` | 1:1 REST wrapper (`BioSimClient`). |
| `interface/plant.py` | `BioSimPlant`, `FakePlant`. A future `HybridPlant` (BioSim + thermal model) goes here. |
| `interface/loop.py` | `run()`, the per-tick pipeline. |
| `interface/log.py` | JSONL tick log, read by the dashboard and evaluation. |
| `configs/mars_lettuce.biosim` | Greenhouse, 10 m² lettuce, solar power, water stores, CO2 injector. |
| `plant_next_steps.md` | Open decisions for the thermal model and plant coupling. |

## Things that will bite you

- **No temperature in the state.** BioSim's 23 °C is a hard-coded constant, so it's deliberately left out. `gh.temp_C` will come from our own thermal model.
- **Commands act one tick later.** A command computed at tick $k$ shows up in the state at tick $k+1$.
- **The XML sets the command ceilings.** `maxFlowRates` in the config is the upper limit for each command, and REST can't raise it. `BioSimPlant.reset()` refuses to run if a ceiling is below the range in `schema.COMMAND_KEYS`.
- **Two settings in the config must stay.** `tickLength="1"` (the crop model assumes it) and `startPaused="true"` (otherwise BioSim ticks on its own). Both are checked at reset.
- **Restart the container now and then.** Every `reset()` starts a new sim on the server, and BioSim never frees old ones.

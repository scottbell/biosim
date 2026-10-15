# Plant model: decisions and next steps

Status as of 2026-10-15. The Python interface to BioSim exists (`interface/`). Nothing in this file is built yet. Each numbered item is a task or decision to split between the two of us working on the plant model.

## Decided

- **Hybrid plant.** BioSim models crops, air, water and power. We write our own Python thermal model for the greenhouse. Both feed one state dict.
- **BioSim is the plant for everything except temperature.** We don't write our own crop model.
- **No Java changes.** Everything lives in `mars_simulation/` in this repo.

## Facts we found in the BioSim source

These limit what the thermal model can do.

- **Air temperature is a constant.** `SimEnvironment.java:20` sets `private final static float temperature = 23f`. There's no setter, no XML attribute and no REST call to change it. BioSim's plant model uses this 23 °C for transpiration (`Plant.java:399-407`) and stomatal conductance (`Planophile.java:51-64`), whatever our thermal model says.
- **The plant's "heat" stress has nothing to do with temperature.** It triggers on light above 300000 (`Plant.java:305`), so in practice it never fires.
- **Lamp power mostly drains the store.**
  - A shelf asks for 1520 W/m² (`Shelf.java:16`).
  - Lettuce only needs about 0.24 W/m² to be fully lit (`Shelf.lightPlants`).
  - Whatever `cmd.crop_power_W` allows is drawn from `Power_Store`. Almost all of it does nothing for growth.
  - So in BioSim, lamp power acts as a power load, and in our thermal model it would act as a heat source. It is not a growth lever.
- **REST can't change a flow that writes into the greenhouse air.**
  - Environment gas stores are named like `Greenhouse.CO2Store` and aren't registered modules. REST updates on those flows fail (`SimulationController.java:892-902`).
  - This is why only the CO2 injector's input side is commanded.
  - This is from reading the code. Confirm it on a live server.
- **No shelf water level, crop biomass, water stress or dead flag over REST.** See `SimulationController.java:572-582` for what a shelf does report.
- **The crop model assumes `tickLength=1`** (`Plant.java:697-702`). Lettuce matures in 672 ticks.
- **Our config uses a Mars sol** (`dayLength="24.66"`) for solar power and light. BioSim's crop clock still counts 24-hour days internally.

## Tasks

1. **Thermal model form.** Start with one lumped node per greenhouse:
   $$C \frac{dT}{dt} = Q_{lamp} + Q_{solar} + Q_{heater} - UA\,(T - T_{ext})$$
   Step it once per BioSim tick ($\Delta t$ = 1 h). Decide whether forward Euler at 1 h is stable for our chosen $C$ and $UA$. It is stable if $\Delta t < C/UA$.

2. **Parameters to source.** $C$ (air plus structure), $UA$ (insulation), the Mars outside temperature profile $T_{ext}(t)$ over a sol, and what fraction of lamp power becomes heat. Find and read a reference for each before using a number.

3. **Lettuce temperature limits.** We need sourced numbers for the optimal range and the stress limits. Read the source before using any value.

4. **Coupling from BioSim into the thermal model (works).** Read from the state dict:
   - `power.crop_draw` → $Q_{lamp}$
   - `gh.light` and `time.hour_of_day` → $Q_{solar}$, if the greenhouse has windows
   - `power.store_level` and `power.solar_out` → how much heater power is available

5. **Coupling from the thermal model back into BioSim (limited).** Pick one or more:
   - (a) **Heater draws real power.** Add an `Injector` with `powerConsumer inputs="Power_Store"` and `powerProducer outputs="Heater_Sink"` (a new `PowerStore`), and command its consumer rate as `cmd.heater_W`. The heater then competes with the lamps for power, which is what makes the dust storm hard. Recommended.
   - (b) **Temperature stress as a growth malfunction.** Schedule a `BiomassPS` malfunction (growth ×0.9 / 0.75 / 0.5 for LOW / MEDIUM / SEVERE) when $T$ leaves the lettuce range. This is coarse, in three steps only. Malfunctions on `Lettuce_Bed` would then mean two things, so the fault log has to tell them apart.
   - (c) **Penalty in the metrics only.** Temperature affects the score in evaluation but not BioSim's crop. Simplest, but the crop doesn't react to temperature.

6. **Interface changes once the model exists.** Make these after tasks 1 to 5 are decided:
   - add `gh.temp_C` to `schema.STATE_KEYS`, and `cmd.heater_W` to `COMMAND_KEYS`;
   - write `HybridPlant` in `plant.py`. It wraps `BioSimPlant` and steps the thermal model after each BioSim tick, using that tick's power numbers.
   - Controllers need no changes.

7. **Dust storm.**
   - Over REST, the only option is a `PowerPS` malfunction on `Solar_Array`: output ×0.5 for SEVERE, and stacking two gives ×0.25.
   - Check on a live server whether clearing a `PERMANENT_MALF` restores full output. Also check what `TEMPORARY_MALF` does to `PowerPS`, since `PowerPS.java:64-85` has to be read for that.
   - For a smooth dimming curve we'd need a Java change. Decide whether stepped is good enough.

8. **Irrigation observability.** The shelf has a 50 L/m² internal reservoir that isn't visible over REST. Decide between:
   - (a) estimating the reservoir from cumulative `water.crop_draw_L` minus transpiration (vapour added to `Greenhouse`);
   - (b) flow-only control on `water.grey_L` and `water.crop_draw_L`.
   Either way, short shortfalls are hidden by the reservoir.

9. **Water recycling.** The config has no `WaterRS`, so dirty water from the dehumidifier is never turned back into grey water. 2000 L of grey water should outlast one lettuce cycle, but check that on a live run. Add `WaterRS` if irrigation is going to be a closed loop.

10. **Live checks once Docker works.**
    - Run `scripts/smoke_test.py` and commit the captured `tests/fixtures/state_sample.json`. The offline tests then run against real output.
    - Confirm REST rejects environment-connected flow updates.
    - Measure milliseconds per tick for 672 ticks.
    - Restart the container between batches, because BioSim never frees old sims.

11. **Fault detection.** Ask whoever owns detection whether `obs`, plus `truth["faults.active"]` and the logged fault schedule, is enough for them to score against.

# Plant model: decisions and next steps

Status as of 2026-10-30. The Python interface to BioSim exists (`interface/`). Water and irrigation (tasks 8 and 9) are built on the `water-irrigation` branch. The thermal tasks are still open. Each numbered item is a task or decision to split between the two of us working on the plant model.

## Decided

- **Hybrid plant.** BioSim models crops, air, water and power. We write our own Python thermal model for the greenhouse. Both feed one state dict.
- **BioSim is the plant for everything except temperature.** We don't write our own crop model.
- **Java changes only where BioSim is broken or hides something we need.** Originally "no Java changes". The water work needed four small patches (below). Ameya's thermal branch makes its own, separate Java changes; that still needs a team decision.

## BioSim patches (water-irrigation branch)

1. **`MathUtils.exp` returned `Math.abs(a)` instead of $e^a$** (`util/MathUtils.java:32`). It's been that way since at least 2008. Its only caller is the crop's saturation vapor pressure (`Plant.java:406`). The plant computed 0.93 kPa instead of 2.81 kPa at 23 °C, so the vapor pressure deficit was clamped to 0 and **no crop in BioSim ever transpired**. Measured before the fix: greenhouse vapor stayed at 41.02 mol for a whole lettuce cycle.
2. **Relative humidity uses the same saturation curve as the plant** (`SimEnvironment.saturatedVaporPressure`, $0.611\,e^{17.4T/(T+239)}$ kPa). Before, `getRelativeHumidity` used $17.4T/(T+273.15)$, so `gh.rh` disagreed with what the plant saw.
3. **`Dehumidifier` takes an optional `targetRelativeHumidity`.** Without it, the dehumidifier only acts above a 0.52 vapor mole fraction, about 18 times saturation, so it never ran. Measured with fix 1 but no target: RH reached 1.000 by tick 201 and transpiration stopped again. With `targetRelativeHumidity="0.7"`, RH holds at 0.700 to 0.718.
4. **The shelf's reservoir is reported over REST** (`waterLevel`, `waterCapacity`). The interface exposes `crop.s0.reservoir_capacity_L` to controllers. `crop.s0.reservoir_L` is truth-only, used to score the estimator.

Java tests: `test/.../environment/HumidityAndTranspirationTest.java` (5 tests). The transpiration test fails on the old `exp`. All 10 Java tests pass.

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
  - Confirmed live on 2026-10-30: 400 without `connections`, 500 with them.
- **No crop biomass, water stress or dead flag over REST.** The shelf water level is now reported (patch 4).
- **Malfunctions on the recycler, injectors and RPCMs are permanent.** They set `maxFlowRates` to 0 (`SingleFlowRateControllable.malfunction`), and clearing doesn't restore them. Measured: recycler broken at tick 300, cleared at 420, and it never treated water again.
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

8. **Irrigation observability.** Done: option (a), in `estimators/water.py`.
   - Uptake = transpiration from the vapor balance, $0.018015\,(\Delta n_{vapor} + n_{dehum})$ L, plus 0.61 L per mol of CO2 taken up. The 0.61 was measured live: 0.608 to 0.610 from tick 60 to tick 660.
   - When the draw is under the cap, the shelf refilled to full, so the previous level is known exactly.
   - Live, one cycle with the controller: RMS error 0.33 L. Within 0.12 L until harvest, then it drifts low (about 0.08 L/tick) because the replanted crop reports the old CO2 uptake. Drifting low is the safe direction.
   - Breaks if greenhouse CO2 runs out: water built into the plant goes to zero, and the estimate drifts high.

9. **Water recycling.** Done.
   - Irrigation now draws from `Potable_Water_Store`. `Water_Recycler` (WaterRS, LINEAR) turns dehumidifier water back into potable water. `Grey_Water_Store` is an unused reserve.
   - `controllers/irrigation.py` keeps a 24 h buffer in the reservoir and runs the recycler on waiting dirty water.

   Live results (`scripts/water_check.py`, 700 ticks):

   | | Fixed cap 20 L/tick | Irrigation controller | Controller, recycler broken from tick 300 |
   |---|---|---|---|
   | Harvest | 35.46 kg | 35.46 kg | 35.46 kg |
   | Drawn into reservoir | 633.2 L | 149.8 L | 149.8 L |
   | Lowest true reservoir | 499.4 L | 4.79 L | 4.79 L |
   | Tank at end | 459.4 L | 942.8 L | 854.4 L |

   Drought sweep (fixed caps): 0.2 L/tick still gave 35.52 kg. 0.1 L/tick gave 19.64 kg. At 0.05 L/tick the crop died.

   Open:
   - Does the team still want irrigation as a headline loop? The reservoir makes it slow and forgiving.
   - Humidity is now a real variable, but its setpoint is fixed in the XML.

10. **Live checks.** Done 2026-10-30, on a local Java 21 build.
    - REST does reject environment-connected flows.
    - 1.4 to 2.4 ms per tick, so a 672-tick cycle takes about 2 s.
    - The captured fixture is regenerated from the patched build. Siddhant's branch has an older one without the new shelf fields; keep the new one when merging.

11. **Fault detection.** Ask whoever owns detection whether `obs`, plus `truth["faults.active"]` and the logged fault schedule, is enough for them to score against.

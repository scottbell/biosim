# Root-zone moisture sensor: design

Status 2026-11-01. Draft for review. Nothing is built yet. This builds on the `water-irrigation` branch (reservoir level, `ReservoirEstimator`, `IrrigationController`).

## Goal

BioSim grows lettuce hydroponically, so "soil moisture" means the water in the shelf's reservoir (the root zone). Right now controllers only get an *estimate* of that level, from `ReservoirEstimator`. A real greenhouse would also measure it directly.

This adds a simulated level sensor and uses the sensor and the estimator to check each other. That gives us:

- a second independent reading of the most important water quantity, and
- a fault-detection story for Phase 3: a broken sensor is caught by the estimator, and a broken estimator is caught by the sensor.

## 1. What it measures

- **Quantity:** reservoir level in **litres**, from truth `crop.s0.reservoir_L`.
- **Why litres and not "% full":** with the irrigation controller the reservoir sits around 5 L out of 500 L. A % reading would hover near 1 %, and its noise would swamp the signal.
- **New observation key:** `crop.s0.root_zone_L` (L). It's `None` when the sensor has no reading.
- **Accuracy matters at the low end, and the geometry makes it hard.** The reservoir is 500 L under a 10 m² shelf, so it's only 50 mm deep. 1 L of water is 0.1 mm of depth.
  - Typical tank level sensors are good to about ±1 to ±3 mm. That covers ultrasonic sensors, and hydrostatic ones at 0.1 % of a 1 m range ([Gems](https://www.gemssensors.com/knowledge/technology-functionality/ultrasonic-level-transmitter-accuracy), [Farnell LVU datasheet](https://www.farnell.com/datasheets/3749935.pdf), [Holykell UE3006](https://www.holykell.com/products/UE3006_Ultrasonic_Water_Level_Sensor.html), [Omega PX633](https://www.globaltestsupply.com/product/omega-px633a1-100gi-submersible-level-transmitter)).
  - Measured straight in the shelf, ±1 mm is **±10 L**. That's twice the whole 5 L buffer.
  - **Assumption:** the bed drains to a narrow sump, and the sensor measures that. With a 0.1 m² sump, ±1 mm becomes ±0.1 L, which is the default `sigma_L = 0.1`. In general $\sigma_L = \sigma_{mm} \times A_{sump}$ (1 mm × 1 m² = 1 L).
  - Weighing the reservoir with load cells is the other realistic option.

## 2. How the sensor sees a hidden value (needs agreement)

`run()` passes `public(truth)` to the sensor layer (`loop.py:89`), so no sensor can read `reservoir_L`. That's on purpose: controllers must never see truth-only keys.

**Proposal:** add `truth` to `TickContext` (one field, one line in `run()`).

- Sensor layers still receive the public state as their first argument.
- A sensor that measures something hidden reads `ctx.truth` and writes its *measurement* under a new key.
- Truth-only keys still never reach controllers, because nothing copies them into `obs`.

Alternative: make `reservoir_L` public and add noise to it in place. That's simpler, but it would leak the true value whenever the sensor layer is missing, and the estimator could no longer be scored against an untouched truth. I don't recommend it.

## 3. Sensor model

The sensor always has some baseline noise (configurable sigma). Faults are added on top, using the same `Fault` records as Toni's `water_power_sensors.py`, with `target="crop.s0.root_zone_L"`:

| kind | effect | params |
|---|---|---|
| `noise` | extra Gaussian noise | `sigma` (L) |
| `bias` | constant offset | `offset` (L) |
| `drift` | offset grows each tick | `rate` (L/tick) |
| `stuck` | repeats the last reading | none |
| `dropout` | reading is `None` | none |

Readings are clipped to 0 to capacity, as a real sensor would be.

**Combining sensor layers.** `run()` takes one `sensor_layer`, but there will be several (Toni's water and power sensors, this one, light, thermal). This needs a small shared helper, `chain(*layers)`, that applies them in order. It's worth agreeing on as a team rather than each of us inventing one.

## 4. Fault detection: sensor vs estimator

- **Residual:** each tick, `r = root_zone_L - est.reservoir_L`.
  - Nominal: the estimator's RMS error is 0.31 L (measured, `water_check.py`), so `r` is that plus sensor noise.
  - Known estimator behaviour: after a harvest it settles about 2.4 L low within ~25 ticks and stays there (see Results).
- **Detection** (one detector per fault signature, so we can tell *what* broke):
  - **Dropout:** reading is `None`.
  - **Stuck:** the reading hasn't changed for N ticks while the estimator has moved more than a threshold.
  - **Bias or drift:** a CUSUM on `r`, with thresholds set from the nominal residual spread.
  - **Estimator broken:** `est.degraded` is set, or CO2 is too low for the uptake model (`gh.co2_ppm` under a threshold). The estimator then drifts *high*, which shows up as a negative `r` even though the sensor is fine.
- **Output to the irrigation controller:**
  - a level to use (`fused_L`),
  - a flag saying which source is trusted,
  - the active fault label, for the intervention log.

## 5. Fusion (stretch)

Combine the two in a small 1-D Kalman filter:

- the estimator's uptake model is the prediction step,
- the sensor is the measurement,
- the measurement is skipped while the sensor is flagged faulty.

This should beat either source alone. I'd write it as a wrapper around `ReservoirEstimator` rather than editing it.

## 6. How we test it

- **Offline:** against `FakePlant`, which already models the reservoir.
- **Live:** a `scripts/root_zone_check.py` scenario run.
  - Nominal run, then each fault kind injected at tick 300 for 120 ticks.
  - Report detection delay, false alarms, the fused level's error against truth, and harvest.

## Results so far (2026-11-02)

Steps 2 and 4 are built: `sensors/root_zone.py` (`RootZoneSensor`) and `estimators/root_zone.py` (`RootZoneMonitor`), with offline tests in `tests/test_root_zone.py`.

`scripts/root_zone_check.py` checks them live without the loop change:

- It runs one cycle on the patched server with `IrrigationController` and CO2 held at 1200 ppm.
- It then replays the log through the sensor and monitor once per scenario: sensor σ = 0.1 L, each fault at tick 300 for 120 ticks.
- `--fake` runs it on FakePlant instead.

Typical run:

| | Result |
|---|---|
| Level error, ticks 30–671 (RMS) | estimate 0.184 L, sensor 0.100 L, **combined 0.092 L** |
| Level error, ticks 30–999 (RMS, 2026-11-10, with re-zeroing) | estimate 1.36–1.62 L, sensor 0.10 L, **combined 0.09 L** |
| False alarms before harvest | 0 |
| False alarms after harvest | before the fix: about 315 "offset" alarms over ticks 684–999. **After the fix: none** (2 runs of 1000 ticks) |
| Estimator marked broken | caught immediately, sensor takes over, worst level error 0.24 L |
| Alarm clears after the fault ends | offsets 23 h, stuck and dropout immediately |
| Bias −2 L | caught after 1 tick, worst level error 1.65 L |
| Drift +0.05 L/tick | caught after 27 ticks, worst level error 1.30 L |
| Stuck | caught after 1 tick, worst level error 0.14 L |
| Dropout | caught immediately, worst level error 0.12 L |

- **Combining helps live.** The combined level is better than either source on its own.
- **The worst errors are the ticks before an offset is caught.** Until then the combined level still trusts the sensor at about 90 % weight.
- **Post-harvest drift, fixed in the monitor (2026-11-10).**
  - After a harvest the estimate settles about 2.4 L low within ~25 ticks and **never recovers**: the replanted crop reports the old CO2 uptake. Over a 1000-tick run that gave about 315 false "offset" alarms.
  - `RootZoneMonitor` now watches `crop.biomass_store_kg`. For 36 ticks after a harvest it uses the sensor alone and flags nothing (`rz.post_harvest`).
  - At the end of that window it re-zeroes the estimate: the mean residual over the last 12 ticks becomes a standing correction (`rz.est_offset_L`).
  - The estimator itself is unchanged. Fixing it at the source would make the re-zero unnecessary.

## 7. Steps (one commit each)

1. Add `ctx.truth`, with a test that controllers still never see truth-only keys.
2. Sensor layer with baseline noise and the five fault kinds, plus offline tests.
3. `chain()` helper for combining sensor layers (if the team agrees).
4. Residual fault detector, plus offline tests.
5. Live scenario script and results note.
6. (Stretch) Kalman fusion.

## Questions for Sophia, and whoever owns irrigation

1. Is the `ctx.truth` loop change okay?
2. Should the irrigation controller take `fused_L` (with a fallback to the estimate), or only the estimate plus a fault flag?
3. Is the key name `crop.s0.root_zone_L` fine?

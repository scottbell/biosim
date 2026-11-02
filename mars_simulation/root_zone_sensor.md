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
- **Accuracy matters at the low end.** A sensor quoted as ±0.25 % of full scale is ±1.25 L on a 500 L tank. That's a quarter of the 5 L buffer. Sensor parameters need a sourced datasheet number before we use them (to do).

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
  - Known estimator behaviour: after harvest it drifts low, about 0.08 L/tick.
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

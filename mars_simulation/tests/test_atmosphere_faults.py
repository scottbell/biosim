"""Comprehensive unit and integration test suite for Atmosphere & Temperature Fault Modeling & Detection (FDIR).

Tests:
1. AtmosphereChannelSensor & AtmosphereSensorLayer:
   - Noise, bias, drift, stuck, and dropout fault injection
   - Alarm band updates and range clamping
   - Chaining multiple sensor layers
2. AtmosphereActuatorLayer:
   - Command stuck, efficiency attenuation, bias, and dropout
   - Range clamping and chaining
3. ThermalEstimator & AtmosphereGasEstimator:
   - Physics-based state propagation, sensor lag matching, and gas conservation
4. AtmosphereTemperatureMonitor:
   - Residual evaluation and CUSUM bias/drift detection
   - Sensor dropout and stuck sensor detection
   - Actuator fault isolation (heater burnout, stuck relay)
   - Resilient inverse-variance telemetry fusion and health diagnostics
"""
from __future__ import annotations

import math
import sys
import unittest
from pathlib import Path

REPO_ROOT = Path(__file__).resolve().parent.parent.parent
SIM_ROOT = REPO_ROOT / "mars_simulation"
if str(SIM_ROOT) not in sys.path:
    sys.path.insert(0, str(SIM_ROOT))

from actuators import ATMOSPHERE_ACTUATOR_KEYS, AtmosphereActuatorLayer, chain_actuator_layers
from controllers.hvac import HVACTemperatureController
from estimators import AtmosphereGasEstimator, AtmosphereTemperatureMonitor, ThermalEstimator
from interface import TickLogger, run
from interface.loop import State, TickContext, merge_commands
from interface.plant import FakePlant, HybridPlant
from interface.schema import Fault
from sensors import (
    ATMOSPHERE_SENSOR_KEYS,
    AtmosphereChannelSensor,
    AtmosphereSensorLayer,
    chain_sensor_layers,
)


class TestAtmosphereSensorFaults(unittest.TestCase):
    """Validates sensor fault simulation (noise, bias, drift, stuck, dropout)."""

    def setUp(self):
        self.sensor = AtmosphereChannelSensor("gh.temp_C", baseline_sigma=0.0, seed=123)

    def test_nominal_pass_through(self):
        val = self.sensor.read(22.0, tick=0)
        self.assertAlmostEqual(val, 22.0, places=4)

    def test_dropout_returns_none(self):
        fault = Fault(tick=5, kind="dropout", target="gh.temp_C")
        self.assertAlmostEqual(self.sensor.read(22.0, tick=4, active_faults=[fault]), 22.0)
        self.assertIsNone(self.sensor.read(22.0, tick=5, active_faults=[fault]))

    def test_bias_injection(self):
        fault = Fault(tick=10, kind="bias", target="gh.temp_C", params={"offset": 4.5})
        val = self.sensor.read(20.0, tick=10, active_faults=[fault])
        self.assertAlmostEqual(val, 24.5, places=4)

    def test_drift_injection(self):
        fault = Fault(tick=10, kind="drift", target="gh.temp_C", params={"rate": 0.25})
        val_t10 = self.sensor.read(20.0, tick=10, active_faults=[fault])
        val_t14 = self.sensor.read(20.0, tick=14, active_faults=[fault])
        # tick 10: offset = 0.25 * (10 - 10 + 1) = 0.25 -> 20.25
        self.assertAlmostEqual(val_t10, 20.25, places=4)
        # tick 14: offset = 0.25 * (14 - 10 + 1) = 1.25 -> 21.25
        self.assertAlmostEqual(val_t14, 21.25, places=4)

    def test_stuck_freezes_reading(self):
        fault = Fault(tick=5, kind="stuck", target="gh.temp_C")
        # Pre-fault reading
        self.sensor.read(21.5, tick=4)
        # Stuck starts
        val1 = self.sensor.read(28.0, tick=5, active_faults=[fault])
        val2 = self.sensor.read(12.0, tick=6, active_faults=[fault])
        self.assertAlmostEqual(val1, 21.5, places=4)
        self.assertAlmostEqual(val2, 21.5, places=4)

    def test_atmosphere_sensor_layer(self):
        layer = AtmosphereSensorLayer(temp_sigma_C=0.0, co2_sigma_ppm=0.0, seed=1)
        truth: State = {
            "gh.temp_C": 22.0,
            "gh.co2_ppm": 1200.0,
            "gh.o2_frac": 0.21,
            "gh.pressure_kPa": 101.3,
            "power.crop_draw": 200.0,
        }
        fault = Fault(tick=2, kind="bias", target="gh.temp_C", params={"offset": 5.0})
        ctx = TickContext(tick=2, active_faults=[fault])

        obs = layer(truth, ctx)
        self.assertAlmostEqual(obs["gh.temp_C"], 27.0, places=4)
        self.assertEqual(obs["gh.temp_alarm"], "WARNING_HIGH")
        # Unaffected channels pass through
        self.assertAlmostEqual(obs["gh.co2_ppm"], 1200.0, places=4)
        self.assertAlmostEqual(obs["power.crop_draw"], 200.0, places=4)

    def test_chain_sensor_layers(self):
        l1 = AtmosphereSensorLayer(temp_sigma_C=0.0, seed=1)
        calls = []

        def custom_layer(truth, ctx):
            calls.append(ctx.tick)
            out = dict(truth)
            out["custom_metric"] = 42.0
            return out

        chained = chain_sensor_layers(l1, custom_layer)
        truth: State = {"gh.temp_C": 20.0}
        ctx = TickContext(tick=7, active_faults=[])
        obs = chained(truth, ctx)
        self.assertIn("custom_metric", obs)
        self.assertEqual(obs["custom_metric"], 42.0)
        self.assertEqual(calls, [7])


class TestAtmosphereActuatorFaults(unittest.TestCase):
    """Validates actuator fault simulation on heater and gas injection."""

    def setUp(self):
        self.actuator_layer = AtmosphereActuatorLayer()

    def test_nominal_pass_through(self):
        cmds = {"cmd.heater_W": 2500.0, "cmd.co2_inject_mol": 2.0}
        ctx = TickContext(tick=0, active_faults=[])
        applied = self.actuator_layer(cmds, ctx)
        self.assertEqual(applied, cmds)

    def test_heater_dropout(self):
        fault = Fault(tick=3, kind="dropout", target="cmd.heater_W")
        ctx = TickContext(tick=3, active_faults=[fault])
        applied = self.actuator_layer({"cmd.heater_W": 3000.0}, ctx)
        self.assertEqual(applied["cmd.heater_W"], 0.0)

    def test_heater_burnout_stuck_zero(self):
        fault = Fault(tick=5, kind="stuck", target="cmd.heater_W", params={"value": 0.0})
        ctx = TickContext(tick=5, active_faults=[fault])
        applied = self.actuator_layer({"cmd.heater_W": 4500.0}, ctx)
        self.assertEqual(applied["cmd.heater_W"], 0.0)

    def test_heater_efficiency_degradation(self):
        fault = Fault(tick=8, kind="efficiency", target="cmd.heater_W", params={"factor": 0.6})
        ctx = TickContext(tick=8, active_faults=[fault])
        applied = self.actuator_layer({"cmd.heater_W": 2000.0}, ctx)
        self.assertAlmostEqual(applied["cmd.heater_W"], 1200.0)

    def test_co2_injector_blocked_stuck_zero(self):
        fault = Fault(tick=2, kind="stuck", target="cmd.co2_inject_mol", params={"value": 0.0})
        ctx = TickContext(tick=2, active_faults=[fault])
        applied = self.actuator_layer({"cmd.co2_inject_mol": 4.0}, ctx)
        self.assertEqual(applied["cmd.co2_inject_mol"], 0.0)

    def test_chain_actuator_layers(self):
        def custom_actuator(commands, ctx):
            out = dict(commands)
            out["cmd.heater_W"] = min(out.get("cmd.heater_W", 0.0), 1000.0)
            return out

        chained = chain_actuator_layers(self.actuator_layer, custom_actuator)
        ctx = TickContext(tick=1, active_faults=[])
        applied = chained({"cmd.heater_W": 3500.0}, ctx)
        self.assertEqual(applied["cmd.heater_W"], 1000.0)


class TestThermalAndGasEstimators(unittest.TestCase):
    """Validates physics-based state estimators."""

    def test_thermal_estimator_equilibrium(self):
        est = ThermalEstimator(initial_temp_C=22.0)
        # At hour 14.0, outside temperature is +19.85 °C
        # Q_in = 0.9 * 2000 W = 1800 W. Delta_T = 1800 / 14 = 128.57 °C
        # T_eq = 19.85 + 128.57 = 148.42 °C
        t_eq = est.predict_equilibrium(hour=14.0, crop_power_W=2000.0, heater_power_W=0.0)
        self.assertAlmostEqual(t_eq, 148.42, places=1)

    def test_thermal_estimator_step_cooling(self):
        est = ThermalEstimator(initial_temp_C=22.0)
        # Step with zero heat input in Mars night
        t_next = est.step(hour=1.0, crop_power_W=0.0, heater_power_W=0.0, dt_hours=1.0)
        self.assertLess(t_next, 22.0)
        # Sensor lag prediction should follow physical temperature
        self.assertLess(est.lag_temp_C, 22.0)

    def test_gas_estimator_conservation(self):
        est = AtmosphereGasEstimator(initial_co2_ppm=1200.0)
        self.assertAlmostEqual(est.co2_ppm, 1200.0, places=2)
        # Inject 1.0 mol CO2 with 0 crop uptake
        ppm_next = est.step(injected_co2_mol=1.0, crop_power_W=0.0)
        # 1.0 mol / 2050 mol * 1e6 = 487.8 ppm increase
        self.assertAlmostEqual(ppm_next, 1200.0 + 487.8, places=0)


class TestAtmosphereTemperatureMonitor(unittest.TestCase):
    """Validates FDIR monitoring, residual evaluation, and fault-tolerant fusion."""

    def setUp(self):
        self.monitor = AtmosphereTemperatureMonitor(
            temp_sensor_sigma_C=0.1,
            temp_model_sigma_C=0.2,
            temp_shift_C=0.8,
            temp_h_sigmas=3.5,
        )
        self.monitor.reset(initial_temp_C=22.0, initial_co2_ppm=1200.0)

    def test_nominal_telemetry_fusion(self):
        obs: State = {
            "gh.temp_C": 22.05,
            "gh.co2_ppm": 1205.0,
            "power.crop_draw": 0.0,
            "power.heater_draw": 0.0,
            "time.hour_of_day": 0.0,
        }
        diag = self.monitor.update(obs, commands={"cmd.heater_W": 0.0})
        self.assertEqual(diag["temp_faults"], [])
        self.assertEqual(diag["temp_trusted"], "both")
        self.assertAlmostEqual(diag["fused_temp_C"], 22.0, delta=0.2)

    def test_detects_temperature_sensor_dropout(self):
        obs: State = {
            "gh.temp_C": None,
            "gh.co2_ppm": 1200.0,
            "power.crop_draw": 0.0,
            "power.heater_draw": 0.0,
            "time.hour_of_day": 0.0,
        }
        diag = self.monitor.update(obs)
        self.assertIn("dropout", diag["temp_faults"])
        self.assertEqual(diag["temp_trusted"], "model")
        # Fused output falls back cleanly to model prediction
        self.assertIsNotNone(diag["fused_temp_C"])

    def test_detects_stuck_temperature_sensor(self):
        # Feed identical sensor reading while model prediction is dropping
        for _ in range(4):
            obs: State = {
                "gh.temp_C": 22.0,
                "gh.co2_ppm": 1200.0,
                "power.crop_draw": 0.0,
                "power.heater_draw": 0.0,
                "time.hour_of_day": 0.0,
            }
            diag = self.monitor.update(obs, commands={"cmd.heater_W": 0.0})

        self.assertIn("stuck", diag["temp_faults"])
        self.assertEqual(diag["temp_trusted"], "model")

    def test_detects_temperature_bias_via_cusum(self):
        # Inject +4.0 °C persistent sensor bias
        detected = False
        for tick in range(10):
            model_expected = self.monitor.thermal_est.lag_temp_C
            obs: State = {
                "gh.temp_C": model_expected + 4.0,
                "gh.co2_ppm": 1200.0,
                "power.crop_draw": 0.0,
                "power.heater_draw": 0.0,
                "time.hour_of_day": float(tick % 24),
            }
            diag = self.monitor.update(obs, commands={"cmd.heater_W": 0.0})
            if "high_bias_or_drift" in diag["temp_faults"]:
                detected = True
                self.assertEqual(diag["temp_trusted"], "model")
                break
        self.assertTrue(detected, "CUSUM failed to detect +4.0 °C temperature bias")

    def test_detects_heater_burnout_actuator_fault(self):
        # Controller demands 2000 W heating, power store has plenty, but actual draw is 0 W
        obs: State = {
            "gh.temp_C": 15.0,
            "gh.co2_ppm": 1200.0,
            "power.store_level": 50000.0,
            "power.crop_draw": 200.0,
            "power.heater_draw": 0.0,
            "time.hour_of_day": 2.0,
        }
        # First tick registers command
        self.monitor.update(obs, commands={"cmd.heater_W": 2000.0})
        # Second tick verifies actual draw remained 0
        diag = self.monitor.update(obs, commands={"cmd.heater_W": 2000.0})
        self.assertIn("heater_burnout", diag["actuator_faults"])
        # In heater burnout, physical sensor is trusted for cabin cooling
        self.assertEqual(diag["temp_trusted"], "sensor")

    def test_detects_co2_sensor_dropout(self):
        obs: State = {
            "gh.temp_C": 22.0,
            "gh.co2_ppm": None,
            "power.crop_draw": 0.0,
            "power.heater_draw": 0.0,
            "time.hour_of_day": 0.0,
        }
        diag = self.monitor.update(obs)
        self.assertIn("dropout", diag["co2_faults"])
        self.assertEqual(diag["co2_trusted"], "model")
        self.assertIsNotNone(diag["fused_co2_ppm"])


class TestClosedLoopFDIRIntegration(unittest.TestCase):
    """Integration test verifying closed-loop HVAC regulation under sensor faults."""

    def test_closed_loop_hvac_tolerates_sensor_dropout(self):
        plant = HybridPlant(plant=FakePlant(), sync_biosim_temperature=False)
        controller = HVACTemperatureController(setpoint_C=22.0)
        monitor = AtmosphereTemperatureMonitor()
        sensor_layer = AtmosphereSensorLayer(temp_sigma_C=0.1)

        truth = plant.reset()
        monitor.reset(float(truth["gh.temp_truth_C"]), float(truth["gh.co2_ppm"]))
        controller.reset()

        dropout_fault = Fault(tick=10, kind="dropout", target="gh.temp_C", duration=15)

        for tick in range(30):
            active = [dropout_fault] if dropout_fault.active_at(tick) else []
            ctx = TickContext(tick=tick, active_faults=active)

            obs = sensor_layer(truth, ctx)
            diag = monitor.update(obs)
            obs.update(diag)

            if tick >= 10 and tick < 25:
                # Confirm monitor caught dropout and provided model fallback
                self.assertIn("dropout", diag["temp_faults"])
                self.assertEqual(diag["temp_trusted"], "model")
                self.assertIsNotNone(obs["fused_temp_C"])

            # Controller uses fused telemetry
            cmds = controller(obs)
            cmds["cmd.crop_power_W"] = 200.0

            monitor.step_prediction(cmds, hour=float(truth.get("time.hour_of_day", 0.0)))
            plant.apply(cmds)
            truth = plant.step()

        # Final cabin temperature should remain healthy despite 15 ticks of sensor dropout
        final_temp = float(truth["gh.temp_truth_C"])
        self.assertGreater(final_temp, 14.0)
        self.assertLess(final_temp, 28.0)


class TestTruthStaysClean(unittest.TestCase):
    """HybridPlant truth holds physical values only; sensing happens in the sensor layer."""

    def test_hybrid_truth_is_physical(self):
        plant = HybridPlant(plant=FakePlant(), sync_biosim_temperature=False)
        plant.reset()
        plant.apply({"cmd.heater_W": 1500.0, "cmd.crop_power_W": 200.0})
        truth = plant.step()
        self.assertEqual(truth["gh.temp_C"], plant.thermal.temp_C)
        self.assertEqual(truth["gh.temp_truth_C"], plant.thermal.temp_C)
        self.assertEqual(truth["gh.co2_ppm"], plant.plant.read()["gh.co2_ppm"])

    def test_controllers_never_see_true_temperature(self):
        seen = []
        run(HybridPlant(plant=FakePlant(), sync_biosim_temperature=False),
            {"spy": lambda obs: seen.append(set(obs)) or {}}, 3,
            sensor_layer=AtmosphereSensorLayer(seed=1))
        self.assertTrue(seen and all("gh.temp_truth_C" not in keys for keys in seen))

    def test_temperature_sync_failure_is_not_swallowed(self):
        class BrokenClient:
            def set_environment_temperature(self, module, temperature_C):
                raise RuntimeError("no temperature endpoint")

        inner = FakePlant()
        inner.client = BrokenClient()
        with self.assertRaises(RuntimeError):
            HybridPlant(plant=inner).reset()


class TestSensorPhysicsInLayer(unittest.TestCase):

    def test_temperature_lag(self):
        layer = AtmosphereSensorLayer(temp_sigma_C=0.0, temp_tau_hours=2.0, seed=1)
        layer({"gh.temp_C": 20.0}, TickContext(tick=0))
        obs = layer({"gh.temp_C": 30.0}, TickContext(tick=1))
        self.assertAlmostEqual(obs["gh.temp_C"], 20.0 + (1.0 - math.exp(-0.5)) * 10.0, places=4)

    def test_co2_cross_sensitivity_uses_true_temperature(self):
        layer = AtmosphereSensorLayer(temp_sigma_C=0.0, co2_sigma_ppm=0.0, co2_temp_cross_sensitivity=5.0, seed=1)
        obs = layer({"gh.temp_C": 27.0, "gh.co2_ppm": 1200.0}, TickContext(tick=0))
        self.assertAlmostEqual(obs["gh.co2_ppm"], 1225.0, places=4)

    def test_alarm_bands_are_open_ended(self):
        from interface.sensors import TemperatureSensorModel, temp_alarm_band
        self.assertEqual(temp_alarm_band(150.0), "SEVERE_HIGH")
        self.assertEqual(temp_alarm_band(-150.0), "SEVERE_LOW")
        self.assertEqual(TemperatureSensorModel().evaluate_alarms(150.0).value, "SEVERE_HIGH")

    def test_biosim_alarm_follows_temperature(self):
        from interface import bindings
        from test_thermal_phase1 import synthetic_raw
        raw = synthetic_raw()
        raw["modules"]["Greenhouse"]["properties"]["temperature"] = 40.0
        self.assertEqual(bindings.to_state(raw)["gh.temp_alarm"], "SEVERE_HIGH")


class TestFaultRecovery(unittest.TestCase):
    """Closed loop through run(): HVAC holds the cabin, and a heater fault doesn't leave the
    monitor blaming the healthy temperature sensor afterwards."""

    @staticmethod
    def _hold_co2(obs):
        co2 = obs.get("gh.co2_ppm")
        mol = 0.0 if co2 is None else (1200.0 - co2) * 1e-6 * 2050.0
        return {"cmd.crop_power_W": 200.0, "cmd.co2_inject_mol": max(0.0, min(5.0, mol))}

    def _run(self, faults, n_ticks):
        monitor = AtmosphereTemperatureMonitor(temp_sensor_sigma_C=0.15, temp_model_sigma_C=0.25,
                                               temp_shift_C=1.0, temp_h_sigmas=4.0)
        diag = {}

        def watch(obs, per_controller, ctx):
            cmds = merge_commands(obs, per_controller, ctx)
            diag[ctx.tick] = monitor.update(obs, commands=cmds)
            return cmds

        log = TickLogger()
        run(HybridPlant(plant=FakePlant(), sync_biosim_temperature=False),
            {"hvac": HVACTemperatureController(), "atmo": self._hold_co2},
            n_ticks, sensor_layer=AtmosphereSensorLayer(seed=42), supervisor=watch,
            actuator_layer=AtmosphereActuatorLayer(), fault_schedule=faults, logger=log)
        return log.records, diag

    def test_hvac_holds_watch_band_over_a_cycle(self):
        records, _ = self._run([], 700)
        temps = [r["truth"]["gh.temp_truth_C"] for r in records]
        self.assertGreaterEqual(min(temps), 18.0)
        self.assertLessEqual(max(temps), 24.0)

    def test_no_false_alarms_over_a_cycle_with_co2_held(self):
        # the CO2 model used to integrate commanded injection against a fixed 2050 mol of air and
        # drifted ~50 ppm by tick 370, after which the healthy CO2 sensor was flagged
        _, diag = self._run([], 700)
        flagged = [t for t, d in diag.items() if d["temp_faults"] or d["co2_faults"] or d["actuator_faults"]]
        self.assertEqual(flagged, [])

    def test_no_sensor_blame_after_heater_burnout(self):
        burnout = Fault(tick=30, kind="stuck", target="cmd.heater_W", params={"value": 0.0}, duration=30)
        records, diag = self._run([burnout], 100)
        self.assertIn("heater_burnout", diag[32]["actuator_faults"])
        after = [diag[t] for t in range(60, 100)]
        self.assertTrue(all(not d["temp_faults"] for d in after))
        self.assertTrue(all(d["temp_trusted"] == "both" for d in after[2:]))
        # before the fix the fused value fell back to a model ~8 °C off and did far worse than the sensor
        def rms(err):
            return math.sqrt(sum(e * e for e in err) / len(err))
        truth = [r["truth"]["gh.temp_truth_C"] for r in records[60:]]
        fused = [diag[r["tick"]]["fused_temp_C"] for r in records[60:]]
        sensed = [r["obs"]["gh.temp_C"] for r in records[60:]]
        self.assertLess(rms([f - t for f, t in zip(fused, truth)]), rms([s - t for s, t in zip(sensed, truth)]))


if __name__ == "__main__":
    unittest.main()

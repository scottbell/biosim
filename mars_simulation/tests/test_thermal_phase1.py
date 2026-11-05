"""Comprehensive unit and integration test suite for Phase 1 Atmosphere & Temperature Utilities.

Tests:
1. GreenhouseThermalModel: Mars diurnal cycle, steady-state equilibrium, and thermal relaxation.
2. MarsAtmosphereCompressorModel: Specific compression work, power draw, and power-limited extraction.
3. TemperatureSensorModel: First-order thermal lag, calibration drift, and 10 alarm bands.
4. GasConcentrationSensorModel: NDIR thermal cross-sensitivity and cabin temperature coupling.
5. HVACTemperatureController: Closed-loop cabin temperature regulation and battery reserve protection.
6. HybridPlant: Integrated cyber-physical loop with thermal ODE, sensor telemetry, and HVAC control.
"""
import math
import unittest
from pathlib import Path
import sys

# Ensure mars_simulation is on python path
REPO_ROOT = Path(__file__).resolve().parent.parent.parent
SIM_ROOT = REPO_ROOT / "mars_simulation"
if str(SIM_ROOT) not in sys.path:
    sys.path.insert(0, str(SIM_ROOT))

from controllers.hvac import HVACTemperatureController
from interface.plant import FakePlant, HybridPlant
from interface.sensors import (
    AlarmSeverity,
    AtmosphereSensorSuite,
    GasConcentrationSensorModel,
    TemperatureSensorModel,
)
from thermal.compressor import MarsAtmosphereCompressorModel
from thermal.model import GreenhouseThermalModel


class TestGreenhouseThermalModel(unittest.TestCase):
    """Validates the continuous lumped-parameter thermal energy balance ODE."""

    def setUp(self):
        self.model = GreenhouseThermalModel(initial_temp_C=22.0)

    def test_mars_diurnal_temperature_oscillation(self):
        """Exterior Martian surface temperature oscillates between 153 K (-120.15 °C) and 293 K (+19.85 °C)."""
        # Peak occurs at sol hour 14.0
        t_peak = self.model.outside_temp_C(14.0)
        self.assertAlmostEqual(t_peak, 19.85, places=2)

        # Trough occurs half a sol later (~1.675 hours or 14.0 + 12.325 = 26.325)
        t_trough = self.model.outside_temp_C(14.0 + 12.325)
        self.assertAlmostEqual(t_trough, -120.15, places=2)

        # Mean temperature is -50.15 °C
        t_quarter = self.model.outside_temp_C(14.0 + 12.325 / 2.0)
        self.assertAlmostEqual(t_quarter, -50.15, places=2)

    def test_steady_state_equilibrium(self):
        """Under constant heat input Q and exterior T_ext, T_in approaches T_ext + Q/UA."""
        # Hold exterior constant by overriding outside_temp_C
        self.model.outside_temp_C = lambda hour: 0.0  # 0 °C outside
        ua = self.model.ua  # 14.0 W/K
        q_heater = 1400.0   # 1400 W electric heating
        # Expected equilibrium: 0 + 1400 / 14 = 100 °C

        # Step for 300 hours (~8 time constants: tau = C/UA = 1771225/14 ≈ 126516s ≈ 35h)
        for hour in range(500):
            self.model.step(hour=hour, lamp_power_W=0.0, heater_power_W=q_heater)

        self.assertAlmostEqual(self.model.temp_C, 100.0, delta=0.5)

    def test_cooling_without_heat(self):
        """Without heating or lamps, habitat cools toward Martian exterior temperature."""
        self.model.outside_temp_C = lambda hour: -50.0
        self.model.temp_C = 22.0

        for hour in range(200):
            self.model.step(hour=hour, lamp_power_W=0.0, heater_power_W=0.0)

        self.assertLess(self.model.temp_C, 0.0)
        self.assertGreater(self.model.temp_C, -50.0)


class TestMarsAtmosphereCompressorModel(unittest.TestCase):
    """Validates the thermodynamic ISRU CO2 compressor model."""

    def setUp(self):
        self.compressor = MarsAtmosphereCompressorModel()

    def test_specific_work_calculation(self):
        """Specific isothermal work across ~0.6 kPa to 70 kPa."""
        # w = (R * T * ln(70 / 0.6)) / 0.65
        # ln(70/0.6) = ln(116.666) ≈ 4.7593
        # 8.314 * 223 * 4.7593 / 0.65 ≈ 13,576 J/mol
        w_spec = self.compressor.specific_work_J_per_mol()
        self.assertGreater(w_spec, 10000.0)
        self.assertLess(w_spec, 20000.0)

    def test_power_required_proportional_to_moles(self):
        """Power requirement scales linearly with desired moles/tick."""
        p_1mol = self.compressor.power_required_W(1.0, tick_hours=1.0)
        p_5mol = self.compressor.power_required_W(5.0, tick_hours=1.0)
        self.assertAlmostEqual(p_5mol, p_1mol * 5.0, places=4)
        self.assertGreater(p_1mol, 0.0)

    def test_power_limited_compression(self):
        """When available power is less than needed, moles compressed is proportionally throttled."""
        p_needed = self.compressor.power_required_W(5.0, tick_hours=1.0)
        half_power = p_needed / 2.0
        actual_moles = self.compressor.moles_compressed(half_power, desired_moles=5.0)
        self.assertAlmostEqual(actual_moles, 2.5, places=3)


class TestSensorPhysicsLayer(unittest.TestCase):
    """Validates realistic sensor physics (lag, drift, noise, cross-sensitivity, and alarm bands)."""

    def test_temperature_sensor_lag(self):
        """Temperature sensor exhibits first-order thermal lag behind step change."""
        # Zero noise and zero drift to test pure lag dynamics
        sensor = TemperatureSensorModel(tau_hours=2.0, drift_rate_per_tick=0.0, noise_std_C=0.0, initial_temp_C=20.0)
        
        # Step input: true temp jumps to 30.0 °C
        # At dt = 1h, alpha = 1 - exp(-1/2) ≈ 0.39347
        reading_1 = sensor.step(true_temp_C=30.0, dt_hours=1.0)
        expected_1 = 20.0 + (1.0 - math.exp(-0.5)) * (30.0 - 20.0)
        self.assertAlmostEqual(reading_1, expected_1, places=3)
        self.assertLess(reading_1, 30.0)

    def test_temperature_sensor_drift(self):
        """Temperature sensor exhibits linear calibration drift over time."""
        drift_rate = 0.05  # 0.05 °C/tick
        sensor = TemperatureSensorModel(tau_hours=0.001, drift_rate_per_tick=drift_rate, noise_std_C=0.0, initial_temp_C=22.0)

        for _ in range(10):
            reading = sensor.step(true_temp_C=22.0)

        # After 10 ticks, drift should be 10 * 0.05 = 0.5 °C
        self.assertAlmostEqual(reading, 22.0 + 0.5, places=3)

    def test_temperature_alarm_bands(self):
        """Temperature sensor correctly classifies the 10 BioSim alarm severity bands."""
        sensor = TemperatureSensorModel(tau_hours=0.001, drift_rate_per_tick=0.0, noise_std_C=0.0)

        self.assertEqual(sensor.evaluate_alarms(22.0), AlarmSeverity.NOMINAL)
        self.assertEqual(sensor.evaluate_alarms(25.0), AlarmSeverity.WATCH_HIGH)
        self.assertEqual(sensor.evaluate_alarms(17.0), AlarmSeverity.WATCH_LOW)
        self.assertEqual(sensor.evaluate_alarms(27.0), AlarmSeverity.WARNING_HIGH)
        self.assertEqual(sensor.evaluate_alarms(14.0), AlarmSeverity.WARNING_LOW)
        self.assertEqual(sensor.evaluate_alarms(30.0), AlarmSeverity.DISTRESS_HIGH)
        self.assertEqual(sensor.evaluate_alarms(10.0), AlarmSeverity.DISTRESS_LOW)
        self.assertEqual(sensor.evaluate_alarms(33.0), AlarmSeverity.CRITICAL_HIGH)
        self.assertEqual(sensor.evaluate_alarms(5.0), AlarmSeverity.CRITICAL_LOW)
        self.assertEqual(sensor.evaluate_alarms(38.0), AlarmSeverity.SEVERE_HIGH)
        self.assertEqual(sensor.evaluate_alarms(0.0), AlarmSeverity.SEVERE_LOW)

    def test_gas_sensor_thermal_cross_sensitivity(self):
        """NDIR CO2 sensor reading drifts with cabin temperature changes."""
        sensor = GasConcentrationSensorModel(
            gas_name="CO2",
            temp_cross_sensitivity=10.0,  # 10 ppm / °C
            calibration_temp_C=22.0,
            noise_std=0.0,
        )
        # At calibration temperature (22 °C): no offset
        reading_cal = sensor.step(true_val=1200.0, cabin_temp_C=22.0)
        self.assertAlmostEqual(reading_cal, 1200.0, places=3)

        # Cabin warms to 27 °C (+5 °C delta): offset = +50 ppm
        reading_warm = sensor.step(true_val=1200.0, cabin_temp_C=27.0)
        self.assertAlmostEqual(reading_warm, 1250.0, places=3)


class TestHVACAndHybridPlantIntegration(unittest.TestCase):
    """Validates cyber-physical closed-loop integration with HybridPlant and HVAC controller."""

    def test_hvac_temperature_regulation(self):
        """HVAC controller regulates cabin temperature toward 22.0 °C in cold Mars environment."""
        hvac = HVACTemperatureController(setpoint_C=22.0, kp=150.0)
        plant = HybridPlant(plant=FakePlant(), sync_biosim_temperature=False)

        state = plant.reset()
        self.assertIn("gh.temp_C", state)
        self.assertIn("gh.temp_truth_C", state)
        self.assertIn("mars.temp_ext_C", state)
        self.assertIn("power.heater_draw", state)

        # Run 48 ticks (approx 2 Martian sols) with active HVAC control and realistic lamp load
        plant.apply({"cmd.crop_power_W": 200.0})
        temps = []
        for _ in range(48):
            cmd = hvac(state)
            cmd["cmd.crop_power_W"] = 200.0
            self.assertIn("cmd.heater_W", cmd)
            self.assertGreaterEqual(cmd["cmd.heater_W"], 0.0)
            self.assertLessEqual(cmd["cmd.heater_W"], 5000.0)

            plant.apply(cmd)
            state = plant.step()
            temps.append(state["gh.temp_C"])

        # Cabin temperature should stay well above freezing despite Mars outside dropping to -120 °C
        final_temp = state["gh.temp_C"]
        self.assertGreater(final_temp, 14.0)
        self.assertLess(final_temp, 28.0)

    def test_hvac_battery_reserve_protection(self):
        """HVAC cuts heater power when electrical storage drops below safe reserve threshold."""
        hvac = HVACTemperatureController(min_power_reserve_Wh=10000.0)
        # Power store has only 5000 Wh, below safety reserve
        obs = {"gh.temp_C": 10.0, "power.store_level": 5000.0}
        cmd = hvac(obs)
        self.assertEqual(cmd["cmd.heater_W"], 0.0)


def _def(flow_type, desired, max_, conns, actual=None):
    return {"type": flow_type,
            "rates": {"maxFlowRates": [max_], "desiredFlowRates": [desired],
                      "actualFlowRates": [desired if actual is None else actual],
                      "initialFlowRates": [desired]},
            "connections": conns}


def _store(name, mtype, level, cap):
    return {"moduleName": name, "moduleType": mtype,
            "properties": {"currentLevel": level, "currentCapacity": cap, "overflow": 0.0, "isPipe": False},
            "malfunctions": []}


def synthetic_raw():
    total = 2050.0
    return {
        "globals": {"myID": 1, "simulationIsPaused": True, "simulationStarted": True,
                    "simulationEnded": False, "ticksGoneBy": 0, "runTillN": -1,
                    "runTillCrewDeath": False, "runTillPlantDeath": False, "looping": False,
                    "driverStutterLength": 0, "tickLength": 1.0},
        "modules": {
            "Greenhouse": {"moduleName": "Greenhouse", "moduleType": "SimEnvironment",
                           "properties": {"temperature": 23.0, "temperatureInKelvin": 296.15,
                                          "lightIntensity": 0.0, "currentVolume": 50000.0,
                                          "initialVolume": 50000.0, "dayLength": 24.66,
                                          "hourOfDayStart": 0.0, "maxLumens": 50000.0,
                                          "relativeHumidity": 0.7, "airLockVolume": 3.7,
                                          "totalPressure": 101.0, "totalMoles": total,
                                          "dangerousOxygenThreshold": 1.0, "leakRate": 0.0,
                                          "initialTotalPressure": 101.0,
                                          "o2Moles": 0.21 * total, "co2Moles": 0.0012 * total,
                                          "otherMoles": 0.0, "vaporMoles": 0.02 * total,
                                          "nitrogenMoles": 0.7688 * total},
                           "malfunctions": []},
            "Dehumidifier": {"moduleName": "Dehumidifier", "moduleType": "Dehumidifier",
                             "consumers": [_def("Air", 1000, 1000, ["Greenhouse"], 10.0)],
                             "producers": [_def("DirtyWater", 1000, 1000, ["Dirty_Water_Store"], 0.18)],
                             "malfunctions": []},
            "Water_Recycler": {"moduleName": "Water_Recycler", "moduleType": "WaterRSLinear",
                               "consumers": [_def("Power", 0, 1540, ["Power_Store"]),
                                             _def("DirtyWater", 10, 10, ["Dirty_Water_Store"], 0),
                                             _def("GreyWater", 0, 10, ["Grey_Water_Store"])],
                               "producers": [_def("PotableWater", 10, 10, ["Potable_Water_Store"], 0)],
                               "malfunctions": []},
            "CO2_Store": _store("CO2_Store", "CO2Store", 200.0, 500.0),
            "Potable_Water_Store": _store("Potable_Water_Store", "PotableWaterStore", 1000.0, 1000.0),
            "Grey_Water_Store": _store("Grey_Water_Store", "GreyWaterStore", 2000.0, 2000.0),
            "Dirty_Water_Store": _store("Dirty_Water_Store", "DirtyWaterStore", 0.0, 5000.0),
            "Power_Store": _store("Power_Store", "PowerStore", 100000.0, 200000.0),
            "Heater_Sink": _store("Heater_Sink", "PowerStore", 0.0, 200000.0),
            "Biomass_Store": _store("Biomass_Store", "BiomassStore", 0.0, 1000.0),
            "Solar_Array": {"moduleName": "Solar_Array", "moduleType": "SolarPowerPS",
                            "consumers": [{"type": "Light"}],
                            "producers": [_def("Power", 20000, 20000, ["Power_Store"], 0)],
                            "malfunctions": []},
            "Lettuce_Bed": {"moduleName": "Lettuce_Bed", "moduleType": "BiomassPS",
                            "consumers": [_def("Power", 2000, 20000, ["Power_Store"]),
                                          _def("PotableWater", 20, 200, ["Potable_Water_Store"]),
                                          _def("GreyWater", 0, 0, ["Grey_Water_Store"]),
                                          _def("Air", 0, 0, ["Greenhouse"])],
                            "producers": [_def("DirtyWater", 100, 100, ["Dirty_Water_Store"], 0),
                                          _def("Biomass", 100, 100, ["Biomass_Store"], 0),
                                          _def("Air", 0, 0, ["Greenhouse"])],
                            "properties": {"shelves": [{"plantType": "LETTUCE", "cropAreaUsed": 10.0,
                                                        "cropAreaTotal": 10.0,
                                                        "timeTillCanopyClosure": 20.0,
                                                        "harvestInterval": 28.0, "ppfNeeded": 295.14,
                                                        "molesOfCO2Inhaled": 0.0,
                                                        "waterLevel": 20.0, "waterCapacity": 500.0}]},
                            "malfunctions": []},
            "CO2_Injector": {"moduleName": "CO2_Injector", "moduleType": "Injector",
                             "consumers": [_def("CO2", 0, 5, ["CO2_Store"])],
                             "producers": [_def("CO2", 5, 5, ["Greenhouse.CO2Store"], 0)],
                             "malfunctions": []},
            "Greenhouse_Heater": {"moduleName": "Greenhouse_Heater", "moduleType": "Injector",
                                  "consumers": [_def("Power", 0, 5000, ["Power_Store"])],
                                  "producers": [_def("Power", 5000, 5000, ["Heater_Sink"], 0)],
                                  "malfunctions": []},
        },
    }


class TestBindingsAndSchema(unittest.TestCase):
    """Validates schema definitions, bindings mapping, and BioSim state translation."""

    def setUp(self):
        self.raw = synthetic_raw()

    def test_every_schema_key_is_produced(self):
        from interface import bindings
        from interface.schema import COMMAND_KEYS, STATE_KEYS, TRUTH_ONLY_KEYS, is_state_key, shelf_key

        state = bindings.to_state(self.raw)
        for key in list(STATE_KEYS) + list(TRUTH_ONLY_KEYS):
            self.assertIn(key, state, f"Missing state key: {key}")
        self.assertIn(shelf_key(0, "harvest_interval"), state)
        self.assertTrue(all(is_state_key(k) for k in state))

    def test_config_validates(self):
        from interface import bindings
        self.assertEqual(bindings.validate(self.raw), [])

    def test_derived_values(self):
        from interface import bindings
        s = bindings.to_state(self.raw)
        self.assertAlmostEqual(s["gh.co2_ppm"], 1200.0, places=1)
        self.assertAlmostEqual(s["water.crop_draw_L"], 20.0, places=1)
        self.assertEqual(s["time.hour_of_day"], 0.0)
        self.assertNotIn("temperature", " ".join(s))

    def test_every_command_has_a_binding(self):
        from interface import bindings
        from interface.schema import COMMAND_KEYS
        self.assertEqual(set(bindings.COMMAND_BINDINGS), set(COMMAND_KEYS))

    def test_missing_module_error_is_readable(self):
        from interface import bindings
        self.raw["modules"].pop("Grey_Water_Store")
        with self.assertRaises(bindings.BindingError):
            bindings.to_state(self.raw)


class TestLoopFakeSimulation(unittest.TestCase):
    """Validates the tick pipeline and loop execution with FakePlant."""

    def test_full_lettuce_cycle_runs_and_logs(self):
        import tempfile
        from interface import FakePlant, TickLogger, read_log, run

        with tempfile.TemporaryDirectory() as tmp_dir:
            path = Path(tmp_dir) / "run.jsonl"
            with TickLogger(path) as log:
                final = run(
                    FakePlant(),
                    {
                        "water": lambda obs: {"cmd.crop_water_L": 2.0},
                        "lamps": lambda obs: {"cmd.crop_power_W": 3000.0},
                        "hvac": HVACTemperatureController(),
                    },
                    n_ticks=672,
                    logger=log,
                )
            records = read_log(path)
            self.assertEqual(len(records), 672)
            self.assertEqual(final["time.tick"], 672)
            self.assertGreater(final["crop.biomass_store_kg"], 0.0)
            first = records[0]
            self.assertEqual(set(first), {"tick", "truth", "obs", "commands", "applied", "faults", "wall_ms"})

    def test_controllers_never_see_truth_only_keys(self):
        from interface import FakePlant, run
        from interface.schema import TRUTH_ONLY_KEYS

        seen = []
        run(FakePlant(), {"spy": lambda obs: seen.append(set(obs)) or {}}, n_ticks=3)
        self.assertTrue(all(not keys & set(TRUTH_ONLY_KEYS) for keys in seen))

    def test_conflicting_controllers_need_a_supervisor(self):
        from interface import FakePlant, run
        with self.assertRaises(ValueError):
            run(FakePlant(), {"a": lambda obs: {"cmd.crop_water_L": 2.0}, "b": lambda obs: {"cmd.crop_water_L": 2.0}}, n_ticks=1)



if __name__ == "__main__":
    unittest.main()

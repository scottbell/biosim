"""Atmosphere and temperature sensor physics models for Martian habitat CPS.

Simulates physical sensor instrumentation imperfections:
- First-order thermal lag (tau)
- Calibration drift (linear with operational time)
- Gaussian measurement noise
- NDIR optical cross-sensitivity to cabin temperature
- 10-band monitoring and alarm evaluation matching BioSim GenericSensor
"""
from __future__ import annotations

import math
import random
from dataclasses import dataclass
from enum import Enum
from typing import Any, Dict, Optional, Tuple


class AlarmSeverity(str, Enum):
    NOMINAL = "NOMINAL"
    WATCH_LOW = "WATCH_LOW"
    WATCH_HIGH = "WATCH_HIGH"
    WARNING_LOW = "WARNING_LOW"
    WARNING_HIGH = "WARNING_HIGH"
    DISTRESS_LOW = "DISTRESS_LOW"
    DISTRESS_HIGH = "DISTRESS_HIGH"
    CRITICAL_LOW = "CRITICAL_LOW"
    CRITICAL_HIGH = "CRITICAL_HIGH"
    SEVERE_LOW = "SEVERE_LOW"
    SEVERE_HIGH = "SEVERE_HIGH"


@dataclass
class AlarmBand:
    min_val: float
    max_val: float
    enabled: bool = True

    def in_range(self, val: float) -> bool:
        return self.enabled and (self.min_val <= val <= self.max_val)


class TemperatureSensorModel:
    """First-order thermal lag, calibration drift, Gaussian noise, and 10 alarm bands.

    Lag Filter:
        alpha = 1 - exp(-dt / tau)
        T_lag(k) = T_lag(k-1) + alpha * (T_true(k) - T_lag(k-1))

    Drift & Noise:
        T_sensed(k) = T_lag(k) + drift_rate * tick + Normal(0, noise_std^2)
    """

    def __init__(
        self,
        tau_hours: float = 0.5,
        drift_rate_per_tick: float = 0.0,
        noise_std_C: float = 0.15,
        initial_temp_C: float = 22.0,
        seed: Optional[int] = None,
    ):
        self.tau = max(0.001, float(tau_hours))
        self.drift_rate = float(drift_rate_per_tick)
        self.noise_std = max(0.0, float(noise_std_C))
        self.rng = random.Random(seed)

        self._lag_temp = float(initial_temp_C)
        self._sensed_temp = float(initial_temp_C)
        self._tick_count = 0
        self._last_alarm = AlarmSeverity.NOMINAL

        # 10 Alarm Bands (default calibrated for Martian lettuce greenhouse: optimal ~22°C)
        self.bands: Dict[AlarmSeverity, AlarmBand] = {
            AlarmSeverity.SEVERE_LOW:    AlarmBand(-100.0, 2.0),
            AlarmSeverity.SEVERE_HIGH:   AlarmBand(35.0, 100.0),
            AlarmSeverity.CRITICAL_LOW:  AlarmBand(2.0, 7.0),
            AlarmSeverity.CRITICAL_HIGH: AlarmBand(32.0, 35.0),
            AlarmSeverity.DISTRESS_LOW:  AlarmBand(7.0, 12.0),
            AlarmSeverity.DISTRESS_HIGH: AlarmBand(29.0, 32.0),
            AlarmSeverity.WARNING_LOW:   AlarmBand(12.0, 16.0),
            AlarmSeverity.WARNING_HIGH:  AlarmBand(26.0, 29.0),
            AlarmSeverity.WATCH_LOW:     AlarmBand(16.0, 18.0),
            AlarmSeverity.WATCH_HIGH:    AlarmBand(24.0, 26.0),
        }

    def reset(self, initial_temp_C: float = 22.0) -> None:
        self._lag_temp = float(initial_temp_C)
        self._sensed_temp = float(initial_temp_C)
        self._tick_count = 0
        self._last_alarm = AlarmSeverity.NOMINAL

    @property
    def sensed_temp(self) -> float:
        return self._sensed_temp

    @property
    def alarm_severity(self) -> AlarmSeverity:
        return self._last_alarm

    def evaluate_alarms(self, val: float) -> AlarmSeverity:
        """Evaluates value against alarm bands in decreasing order of severity."""
        priority_order = [
            AlarmSeverity.SEVERE_LOW,
            AlarmSeverity.SEVERE_HIGH,
            AlarmSeverity.CRITICAL_LOW,
            AlarmSeverity.CRITICAL_HIGH,
            AlarmSeverity.DISTRESS_LOW,
            AlarmSeverity.DISTRESS_HIGH,
            AlarmSeverity.WARNING_LOW,
            AlarmSeverity.WARNING_HIGH,
            AlarmSeverity.WATCH_LOW,
            AlarmSeverity.WATCH_HIGH,
        ]
        for severity in priority_order:
            band = self.bands.get(severity)
            if band and band.in_range(val):
                return severity
        return AlarmSeverity.NOMINAL

    def step(self, true_temp_C: float, dt_hours: float = 1.0) -> float:
        """Updates internal lag state, adds drift and noise, and evaluates alarms."""
        alpha = 1.0 - math.exp(-dt_hours / self.tau)
        self._lag_temp += alpha * (float(true_temp_C) - self._lag_temp)
        self._tick_count += 1

        drift = self.drift_rate * self._tick_count
        noise = self.rng.gauss(0.0, self.noise_std) if self.noise_std > 0 else 0.0

        self._sensed_temp = self._lag_temp + drift + noise
        self._last_alarm = self.evaluate_alarms(self._sensed_temp)
        return self._sensed_temp


class GasConcentrationSensorModel:
    """Gas concentration sensor with thermal cross-sensitivity, drift, and noise.

    Thermal Cross-Sensitivity (e.g. NDIR optical detectors):
        Reading drifts as cabin temperature deviates from factory calibration temp (22°C):
        offset = temp_cross_sensitivity * (T_cabin - calibration_temp_C)
    """

    def __init__(
        self,
        gas_name: str = "CO2",
        temp_cross_sensitivity: float = 0.0,
        calibration_temp_C: float = 22.0,
        drift_rate_per_tick: float = 0.0,
        noise_std: float = 0.0,
        seed: Optional[int] = None,
    ):
        self.gas_name = gas_name
        self.temp_cross_sensitivity = float(temp_cross_sensitivity)
        self.calibration_temp_C = float(calibration_temp_C)
        self.drift_rate = float(drift_rate_per_tick)
        self.noise_std = max(0.0, float(noise_std))
        self.rng = random.Random(seed)
        self._tick_count = 0
        self._sensed_val = 0.0

    def reset(self) -> None:
        self._tick_count = 0
        self._sensed_val = 0.0

    @property
    def sensed_value(self) -> float:
        return self._sensed_val

    def step(self, true_val: float, cabin_temp_C: float) -> float:
        self._tick_count += 1
        temp_delta = float(cabin_temp_C) - self.calibration_temp_C
        thermal_offset = self.temp_cross_sensitivity * temp_delta
        drift = self.drift_rate * self._tick_count
        noise = self.rng.gauss(0.0, self.noise_std) if self.noise_std > 0 else 0.0

        sensed = float(true_val) + thermal_offset + drift + noise
        self._sensed_val = max(0.0, sensed)
        return self._sensed_val


class AtmosphereSensorSuite:
    """Atmosphere sensor suite bundling cabin temperature and gas sensors."""

    def __init__(
        self,
        tau_temp_hours: float = 0.5,
        temp_drift_rate: float = 0.0,
        temp_noise_std: float = 0.15,
        co2_temp_cross_sensitivity: float = 5.0,  # ppm/°C
        co2_noise_std: float = 10.0,              # ppm
        o2_noise_std: float = 0.001,              # mole fraction
        seed: Optional[int] = None,
    ):
        self.temp_sensor = TemperatureSensorModel(
            tau_hours=tau_temp_hours,
            drift_rate_per_tick=temp_drift_rate,
            noise_std_C=temp_noise_std,
            seed=seed,
        )
        self.co2_sensor = GasConcentrationSensorModel(
            gas_name="CO2",
            temp_cross_sensitivity=co2_temp_cross_sensitivity,
            noise_std=co2_noise_std,
            seed=seed + 1 if seed is not None else None,
        )
        self.o2_sensor = GasConcentrationSensorModel(
            gas_name="O2",
            temp_cross_sensitivity=0.0,
            noise_std=o2_noise_std,
            seed=seed + 2 if seed is not None else None,
        )

    def reset(self, initial_temp_C: float = 22.0) -> None:
        self.temp_sensor.reset(initial_temp_C)
        self.co2_sensor.reset()
        self.o2_sensor.reset()

    def process(
        self,
        true_temp_C: float,
        true_co2_ppm: float,
        true_o2_frac: float,
        dt_hours: float = 1.0,
    ) -> Dict[str, Any]:
        """Process physical ground-truth values into realistic sensed telemetry."""
        sensed_temp = self.temp_sensor.step(true_temp_C, dt_hours=dt_hours)
        sensed_co2 = self.co2_sensor.step(true_co2_ppm, cabin_temp_C=sensed_temp)
        sensed_o2 = self.o2_sensor.step(true_o2_frac, cabin_temp_C=sensed_temp)

        return {
            "gh.temp_C": sensed_temp,
            "gh.temp_alarm": self.temp_sensor.alarm_severity.value,
            "gh.co2_ppm": sensed_co2,
            "gh.o2_frac": sensed_o2,
        }

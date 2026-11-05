"""Atmosphere & Temperature State Estimation and Fault Detection (FDIR).

Architecture:
1. ThermalEstimator: Physics-based model estimator integrating the continuous
   energy balance ODE: C * dT/dt = Q_lamp + Q_solar + Q_heater - UA * (T - T_ext).
2. AtmosphereGasEstimator: Conservation-of-mass estimator for greenhouse CO2
   concentrations from injection and crop uptake.
3. AtmosphereTemperatureMonitor: Cross-checks sensors against physics estimators
   using statistical residual testing and two-sided CUSUM. Detects:
   - Sensor faults: dropout, stuck, bias, drift, NDIR thermal cross-sensitivity
   - Actuator/plant faults: heater burnout/open circuit, heater stuck-on, thermal envelope breach
   Produces optimal inverse-variance fused telemetry (fused_temp_C, fused_co2_ppm)
   and health diagnostics for downstream supervisory control.
"""
from __future__ import annotations

from collections import deque
import math
from typing import Any, Dict, List, Optional, Tuple


class ThermalEstimator:
    """Physics-based greenhouse thermal state estimator with sensor lag modeling."""

    MARS_SOL_HOURS = 24.66
    PEAK_HOUR = 14.0
    T_MEAN_C = -50.15
    T_AMP_C = 70.0
    THERMAL_CAPACITANCE_J_PER_K = 1_771_225.0
    UA_W_PER_K = 14.0
    LAMP_HEAT_FRACTION = 0.9

    def __init__(
        self,
        initial_temp_C: float = 22.0,
        thermal_capacitance: float = THERMAL_CAPACITANCE_J_PER_K,
        ua: float = UA_W_PER_K,
        sensor_tau_hours: float = 0.5,
    ):
        self.C = float(thermal_capacitance)
        self.UA = float(ua)
        self.tau = max(0.001, float(sensor_tau_hours))
        self.temp_C = float(initial_temp_C)
        self.lag_temp_C = float(initial_temp_C)

    def reset(self, initial_temp_C: float = 22.0) -> None:
        self.temp_C = float(initial_temp_C)
        self.lag_temp_C = float(initial_temp_C)

    def outside_temp_C(self, hour_in_sol: float) -> float:
        phase = (2.0 * math.pi / self.MARS_SOL_HOURS) * ((hour_in_sol - self.PEAK_HOUR) % self.MARS_SOL_HOURS)
        return self.T_MEAN_C + self.T_AMP_C * math.cos(phase)

    def step(
        self,
        hour: float,
        crop_power_W: float,
        heater_power_W: float,
        dt_hours: float = 1.0,
    ) -> float:
        """Propagates thermal ODE and sensor lag forward."""
        t_ext = self.outside_temp_C(hour)
        q_in = (self.LAMP_HEAT_FRACTION * max(0.0, crop_power_W)) + max(0.0, heater_power_W)
        t_eq = t_ext + (q_in / self.UA)

        dt_sec = dt_hours * 3600.0
        k = self.UA / self.C
        self.temp_C = t_eq + (self.temp_C - t_eq) * math.exp(-k * dt_sec)

        # First-order sensor thermal lag response
        alpha = 1.0 - math.exp(-dt_hours / self.tau)
        self.lag_temp_C += alpha * (self.temp_C - self.lag_temp_C)
        return self.temp_C

    def predict_equilibrium(self, hour: float, crop_power_W: float, heater_power_W: float) -> float:
        t_ext = self.outside_temp_C(hour)
        q_in = (self.LAMP_HEAT_FRACTION * max(0.0, crop_power_W)) + max(0.0, heater_power_W)
        return t_ext + (q_in / self.UA)


class AtmosphereGasEstimator:
    """CO2 and atmospheric gas concentration estimator based on gas balance."""

    TOTAL_MOLES = 2050.0
    CO2_PEAK_UPTAKE = 0.30  # mol/tick at lettuce maturity
    CYCLE_HOURS = 672.0

    def __init__(self, initial_co2_ppm: float = 1200.0, total_moles: float = TOTAL_MOLES):
        self.total_moles = float(total_moles)
        self.co2_moles = (float(initial_co2_ppm) / 1e6) * self.total_moles
        self.age_h = 0

    def reset(self, initial_co2_ppm: float = 1200.0) -> None:
        self.co2_moles = (float(initial_co2_ppm) / 1e6) * self.total_moles
        self.age_h = 0

    @property
    def co2_ppm(self) -> float:
        return (self.co2_moles / self.total_moles) * 1e6

    def step(
        self,
        injected_co2_mol: float,
        crop_power_W: float,
        co2_inhaled_mol: Optional[float] = None,
    ) -> float:
        """Steps gas mole balance forward."""
        if co2_inhaled_mol is not None and co2_inhaled_mol >= 0:
            uptake = float(co2_inhaled_mol)
        elif crop_power_W > 0.0:
            growth_frac = min(1.0, self.age_h / self.CYCLE_HOURS)
            uptake = self.CO2_PEAK_UPTAKE * (growth_frac ** 2.5)
        else:
            uptake = 0.0

        injected = max(0.0, float(injected_co2_mol))
        self.co2_moles = max(0.0, self.co2_moles + injected - uptake)
        self.age_h = (self.age_h + 1) % int(self.CYCLE_HOURS)
        return self.co2_ppm


class AtmosphereTemperatureMonitor:
    """Statistical residual evaluator and fault detector for atmosphere and temperature.

    Cross-checks sensor readings against physics model estimators to detect:
    - Sensor dropout, stuck values, bias, and drift (using two-sided CUSUM)
    - Actuator faults (heater burnout/failure, stuck-on relay)
    - Thermal envelope integrity degradation
    - NDIR temperature cross-sensitivity drift
    """

    def __init__(
        self,
        temp_sensor_sigma_C: float = 0.15,
        temp_model_sigma_C: float = 0.25,
        co2_sensor_sigma_ppm: float = 5.0,
        co2_model_sigma_ppm: float = 25.0,
        temp_shift_C: float = 1.0,
        temp_h_sigmas: float = 4.5,
        co2_shift_ppm: float = 40.0,
        co2_h_sigmas: float = 4.5,
        stuck_ticks: int = 3,
        clear_ticks: int = 8,
    ):
        self.temp_sensor_var = float(temp_sensor_sigma_C) ** 2
        self.temp_model_var = float(temp_model_sigma_C) ** 2
        self.temp_spread = math.sqrt(self.temp_sensor_var + self.temp_model_var)
        self.temp_k = temp_shift_C / 2.0
        self.temp_h = temp_h_sigmas * self.temp_spread

        self.co2_sensor_var = float(co2_sensor_sigma_ppm) ** 2
        self.co2_model_var = float(co2_model_sigma_ppm) ** 2
        self.co2_spread = math.sqrt(self.co2_sensor_var + self.co2_model_var)
        self.co2_k = co2_shift_ppm / 2.0
        self.co2_h = co2_h_sigmas * self.co2_spread

        self.stuck_ticks = int(stuck_ticks)
        self.clear_ticks = int(clear_ticks)

        self.thermal_est = ThermalEstimator()
        self.gas_est = AtmosphereGasEstimator()
        self.reset()

    def reset(self, initial_temp_C: float = 22.0, initial_co2_ppm: float = 1200.0) -> None:
        self.thermal_est.reset(initial_temp_C)
        self.gas_est.reset(initial_co2_ppm)

        # Temperature CUSUM state
        self.temp_g_pos = 0.0
        self.temp_g_neg = 0.0
        self.temp_offset_alarm = False
        self._temp_calm = 0
        self._prev_temp_sensor: Optional[float] = None
        self._same_temp_count = 0
        self._recent_temp_est: deque[float] = deque(maxlen=self.stuck_ticks + 1)

        # CO2 CUSUM state
        self.co2_g_pos = 0.0
        self.co2_g_neg = 0.0
        self.co2_offset_alarm = False
        self._co2_calm = 0
        self._prev_co2_sensor: Optional[float] = None
        self._same_co2_count = 0
        self._recent_co2_est: deque[float] = deque(maxlen=self.stuck_ticks + 1)

        # Actuator command memory from step k-1 -> k
        self._last_cmd_heater = 0.0
        self._last_cmd_co2 = 0.0
        self._heater_unresponsive_count = 0

    def _check_stuck(
        self,
        current: float,
        previous: Optional[float],
        count: int,
        history: deque[float],
        sensor_var: float,
        move_threshold: float,
    ) -> Tuple[bool, int]:
        history.append(current)
        if previous is not None and current == previous:
            count += 1
        else:
            count = 0

        if count < self.stuck_ticks - 1:
            return False, count
        if sensor_var > 0:
            return True, count
        # For noiseless measurements, confirm model expected variation
        is_stuck = (max(history) - min(history)) > move_threshold
        return is_stuck, count

    def update(
        self,
        obs: Dict[str, Any],
        commands: Optional[Dict[str, float]] = None,
    ) -> Dict[str, Any]:
        """Processes current observations and commands to detect faults and fuse telemetry."""
        commands = commands or {}
        hour = float(obs.get("time.hour_of_day", 0.0))
        power_store = float(obs.get("power.store_level", float("inf")))

        # 1. Evaluate Actuator Health from last tick's command vs current tick's reported draw
        actuator_faults: List[str] = []
        actual_heater_draw = float(obs.get("power.heater_draw", 0.0))
        actual_co2_injected = float(obs.get("co2.injected_mol", 0.0))

        if self._last_cmd_heater > 500.0 and power_store > 1000.0 and actual_heater_draw < 10.0:
            self._heater_unresponsive_count += 1
            if self._heater_unresponsive_count >= 1:
                actuator_faults.append("heater_burnout")
        elif self._last_cmd_heater == 0.0 and actual_heater_draw > 500.0:
            actuator_faults.append("heater_stuck_on")
        else:
            self._heater_unresponsive_count = max(0, self._heater_unresponsive_count - 1)

        if self._last_cmd_co2 > 1.0 and actual_co2_injected < 0.01:
            actuator_faults.append("co2_injector_blocked")

        # 2. Model predictions for current tick k
        model_temp_sensor = self.thermal_est.lag_temp_C
        true_model_temp = self.thermal_est.temp_C
        model_co2 = self.gas_est.co2_ppm

        temp_faults: List[str] = []
        co2_faults: List[str] = []

        # 3. Check Temperature Sensed Telemetry at tick k
        sensed_temp: Optional[float] = obs.get("gh.temp_C")
        temp_sensor_healthy = True
        r_temp: Optional[float] = None

        if sensed_temp is None:
            temp_faults.append("dropout")
            temp_sensor_healthy = False
            self._prev_temp_sensor = None
            self._same_temp_count = 0
        else:
            is_stuck, self._same_temp_count = self._check_stuck(
                current=sensed_temp,
                previous=self._prev_temp_sensor,
                count=self._same_temp_count,
                history=self._recent_temp_est,
                sensor_var=self.temp_sensor_var,
                move_threshold=0.2,
            )
            self._prev_temp_sensor = sensed_temp
            if is_stuck:
                temp_faults.append("stuck")
                temp_sensor_healthy = False

            r_temp = sensed_temp - model_temp_sensor

            # If heater failed physically, temperature naturally drops; don't misattribute to sensor bias
            if "heater_burnout" not in actuator_faults and temp_sensor_healthy:
                self.temp_g_pos = max(0.0, self.temp_g_pos + r_temp - self.temp_k)
                self.temp_g_neg = max(0.0, self.temp_g_neg - r_temp - self.temp_k)

                if self.temp_g_pos > self.temp_h:
                    self.temp_offset_alarm = True
                    temp_faults.append("high_bias_or_drift")
                elif self.temp_g_neg > self.temp_h:
                    self.temp_offset_alarm = True
                    temp_faults.append("low_bias_or_drift")

                if self.temp_offset_alarm:
                    temp_sensor_healthy = False
                    if abs(r_temp) < 2.0 * self.temp_spread:
                        self._temp_calm += 1
                        if self._temp_calm >= self.clear_ticks:
                            self.temp_offset_alarm = False
                            self._temp_calm = 0
                            self.temp_g_pos = 0.0
                            self.temp_g_neg = 0.0
                    else:
                        self._temp_calm = 0

        # Check thermal envelope breach
        ext_temp = self.thermal_est.outside_temp_C(hour)
        if actual_heater_draw >= 4500.0 and true_model_temp < 10.0 and sensed_temp is not None and sensed_temp < 5.0 and ext_temp < -40.0:
            actuator_faults.append("thermal_envelope_breach")

        # 4. Check CO2 Sensed Telemetry at tick k
        sensed_co2: Optional[float] = obs.get("gh.co2_ppm")
        co2_sensor_healthy = True
        r_co2: Optional[float] = None

        if sensed_co2 is None:
            co2_faults.append("dropout")
            co2_sensor_healthy = False
            self._prev_co2_sensor = None
            self._same_co2_count = 0
        else:
            is_stuck_co2, self._same_co2_count = self._check_stuck(
                current=sensed_co2,
                previous=self._prev_co2_sensor,
                count=self._same_co2_count,
                history=self._recent_co2_est,
                sensor_var=self.co2_sensor_var,
                move_threshold=5.0,
            )
            self._prev_co2_sensor = sensed_co2
            if is_stuck_co2:
                co2_faults.append("stuck")
                co2_sensor_healthy = False

            r_co2 = sensed_co2 - model_co2

            if co2_sensor_healthy:
                self.co2_g_pos = max(0.0, self.co2_g_pos + r_co2 - self.co2_k)
                self.co2_g_neg = max(0.0, self.co2_g_neg - r_co2 - self.co2_k)

                if self.co2_g_pos > self.co2_h:
                    self.co2_offset_alarm = True
                    co2_faults.append("high_bias_or_drift")
                elif self.co2_g_neg > self.co2_h:
                    self.co2_offset_alarm = True
                    co2_faults.append("low_bias_or_drift")

                if self.co2_offset_alarm:
                    co2_sensor_healthy = False
                    if abs(r_co2) < 2.0 * self.co2_spread:
                        self._co2_calm += 1
                        if self._co2_calm >= self.clear_ticks:
                            self.co2_offset_alarm = False
                            self._co2_calm = 0
                            self.co2_g_pos = 0.0
                            self.co2_g_neg = 0.0
                    else:
                        self._co2_calm = 0

            # NDIR thermal cross-sensitivity: only flag if residual closely matches temp deviation
            if sensed_temp is not None and abs(sensed_temp - 22.0) > 8.0:
                expected_ndir_shift = (sensed_temp - 22.0) * 5.0
                if abs(r_co2 - expected_ndir_shift) < self.co2_spread:
                    co2_faults.append("ndir_thermal_shift")

        # 5. Fault-Tolerant Fusion
        # Temperature Fusion
        if "heater_burnout" in actuator_faults and sensed_temp is not None:
            fused_temp = sensed_temp
            temp_trusted = "sensor"
        elif temp_sensor_healthy and sensed_temp is not None:
            w_temp = self.temp_model_var / (self.temp_sensor_var + self.temp_model_var)
            fused_temp = (w_temp * sensed_temp) + ((1.0 - w_temp) * true_model_temp)
            temp_trusted = "both"
        else:
            fused_temp = true_model_temp
            temp_trusted = "model"

        # CO2 Fusion
        if co2_sensor_healthy and sensed_co2 is not None:
            w_co2 = self.co2_model_var / (self.co2_sensor_var + self.co2_model_var)
            fused_co2 = (w_co2 * sensed_co2) + ((1.0 - w_co2) * model_co2)
            co2_trusted = "both"
        else:
            fused_co2 = model_co2
            co2_trusted = "model"

        # 6. Step physics estimators for step k -> k+1 if commands are explicitly passed
        if commands:
            self.step_prediction(
                commands=commands,
                hour=hour,
                actuator_faults=actuator_faults,
                inhaled_co2=obs.get("crop.s0.co2_inhaled_mol"),
            )

        return {
            "fused_temp_C": fused_temp,
            "temp_model_est_C": true_model_temp,
            "temp_residual_C": r_temp,
            "temp_trusted": temp_trusted,
            "temp_faults": temp_faults,
            "actuator_faults": actuator_faults,
            "fused_co2_ppm": fused_co2,
            "co2_model_est_ppm": model_co2,
            "co2_residual_ppm": r_co2,
            "co2_trusted": co2_trusted,
            "co2_faults": co2_faults,
        }

    def step_prediction(
        self,
        commands: Dict[str, float],
        hour: float,
        actuator_faults: Optional[List[str]] = None,
        inhaled_co2: Optional[float] = None,
    ) -> None:
        """Propagates model predictions to tick k+1 using commands acting during k -> k+1."""
        faults = actuator_faults or []
        next_cmd_heater = float(commands.get("cmd.heater_W", 0.0))
        next_cmd_crop = float(commands.get("cmd.crop_power_W", 200.0))
        next_cmd_co2 = float(commands.get("cmd.co2_inject_mol", 0.0))

        effective_heater_power = 0.0 if "heater_burnout" in faults else next_cmd_heater
        effective_co2_inject = 0.0 if "co2_injector_blocked" in faults else next_cmd_co2

        next_hour = (hour + 1.0) % self.thermal_est.MARS_SOL_HOURS
        self.thermal_est.step(hour=next_hour, crop_power_W=next_cmd_crop, heater_power_W=effective_heater_power)
        self.gas_est.step(
            injected_co2_mol=effective_co2_inject,
            crop_power_W=next_cmd_crop,
            co2_inhaled_mol=inhaled_co2,
        )

        self._last_cmd_heater = next_cmd_heater
        self._last_cmd_co2 = next_cmd_co2

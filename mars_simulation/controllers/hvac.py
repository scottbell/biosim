"""HVAC temperature controller for Martian greenhouse habitat."""
from __future__ import annotations

from typing import Any, Dict, Optional

from thermal.model import GreenhouseThermalModel


class HVACTemperatureController:
    """Proportional-Integral-Derivative (PID) controller for greenhouse cabin heating.

    Target Setpoint:
        T_setpoint = 22.0 °C (lettuce optimal metabolic temperature)
    
    Actuation:
        Outputs `cmd.heater_W` in [0.0, max_heating_W] (default 5000 W max).
        Features anti-windup clamping and battery energy reserve protection.

    Feedforward:
        Q_ff = UA * (T_setpoint - T_ext) - lamp_heat_fraction * P_lamp
        cancels the heat lost through the hull, using mars.temp_ext_C and power.crop_draw.
        Without it the PID has to carry a ~1 kW diurnal swing on its integral alone and
        the cabin swings far outside the WATCH band every sol.
    """

    def __init__(
        self,
        setpoint_C: float = 22.0,
        kp: float = 300.0,
        ki: float = 10.0,
        kd: float = 0.0,
        max_heating_W: float = 5000.0,
        min_power_reserve_Wh: float = 5000.0,
        ua_W_per_K: float = GreenhouseThermalModel.UA,
        lamp_heat_fraction: float = GreenhouseThermalModel.LAMP_HEAT_FRACTION,
    ):
        self.setpoint_C = float(setpoint_C)
        self.kp = float(kp)
        self.ki = float(ki)
        self.kd = float(kd)
        self.max_heating_W = float(max_heating_W)
        self.min_power_reserve = float(min_power_reserve_Wh)
        self.ua = float(ua_W_per_K)
        self.lamp_heat_fraction = float(lamp_heat_fraction)

        self._integral_error = 0.0
        self._last_error: Optional[float] = None

    def reset(self) -> None:
        self._integral_error = 0.0
        self._last_error = None

    def __call__(self, obs: Dict[str, Any]) -> Dict[str, float]:
        """Calculates heating power command from sensed or fused temperature observation."""
        temp_val = obs.get("fused_temp_C")
        if temp_val is None:
            temp_val = obs.get("gh.temp_C")
        if temp_val is None:
            temp_val = self.setpoint_C
        temp_sensed = float(temp_val)
        error = self.setpoint_C - temp_sensed

        # Derivative term
        if self._last_error is not None:
            derivative = error - self._last_error
        else:
            derivative = 0.0
        self._last_error = error

        # Proportional and Derivative contribution
        p_term = self.kp * error
        d_term = self.kd * derivative

        # Check battery reserve: don't exhaust power store if below safety threshold
        power_store = float(obs.get("power.store_level", float("inf")))
        if power_store < self.min_power_reserve:
            return {"cmd.heater_W": 0.0}

        t_ext = obs.get("mars.temp_ext_C")
        ff_term = 0.0
        if t_ext is not None:
            lamp_W = float(obs.get("power.crop_draw") or 0.0)
            ff_term = self.ua * (self.setpoint_C - float(t_ext)) - self.lamp_heat_fraction * lamp_W

        # Raw desired power
        desired_power = ff_term + p_term + (self.ki * self._integral_error) + d_term

        # Clamp command to [0, max_heating_W]
        heater_power = max(0.0, min(self.max_heating_W, desired_power))

        # Anti-windup conditional integration
        if 0.0 < desired_power < self.max_heating_W:
            self._integral_error += error

        return {"cmd.heater_W": heater_power}

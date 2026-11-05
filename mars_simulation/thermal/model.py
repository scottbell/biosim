"""Continuous thermal energy balance ODE for the Martian greenhouse habitat."""
from __future__ import annotations

import math
from typing import Optional


class GreenhouseThermalModel:
    """Lumped-parameter thermal model for a Martian bioregenerative greenhouse.

    Governing Energy Balance ODE:
        C_th * (dT_in / dt) = Q_lamp + Q_heater + Q_biogenic - UA * (T_in - T_mars(t))

    Physical Parameters:
        C_th: 1,771,225 J/K (effective thermal capacitance of structural shell, cabin air & water buffers)
        UA:   14.0 W/K (overall hull heat transfer coefficient)
        Sol:  24.65 hours
        T_ext: Diurnal oscillation between 153 K (-120 °C) and 293 K (+20 °C)
    """

    # Thermal parameters established for the habitat
    HEAT_CAPACITY = 1_771_225.0  # J/K (C_th)
    UA = 14.0                    # W/K (overall heat transfer coefficient)
    TICK_SECONDS = 3600.0        # 1 BioSim tick = 1 hour = 3600 seconds

    # Heat conversion fractions
    LAMP_HEAT_FRACTION = 0.90    # 90% of electric lamp power dissipates as heat
    HEATER_EFFICIENCY = 1.00     # Electric resistive heating efficiency

    # Mars Sol parameters
    MARS_SOL_HOURS = 24.65
    T_MEAN_C = -50.15            # Mean Mars surface temp (223 K - 273.15)
    T_AMP_C = 70.00              # Amplitude: peak +19.85 °C (293 K), trough -120.15 °C (153 K)
    PEAK_HOUR = 14.00            # Diurnal thermal peak around mid-afternoon (sol hour 14.0)

    def __init__(self, initial_temp_C: float = 22.0, c_th: Optional[float] = None, ua: Optional[float] = None):
        self.initial_temp_C = float(initial_temp_C)
        self.temp_C = float(initial_temp_C)
        self.c_th = float(c_th) if c_th is not None else self.HEAT_CAPACITY
        self.ua = float(ua) if ua is not None else self.UA

    def reset(self) -> None:
        """Reset habitat temperature to initial setpoint."""
        self.temp_C = float(self.initial_temp_C)

    def outside_temp_C(self, hour: float) -> float:
        """Compute exterior Martian surface temperature at a given sol hour."""
        hour_in_sol = hour % self.MARS_SOL_HOURS
        phase = (2.0 * math.pi / self.MARS_SOL_HOURS) * (hour_in_sol - self.PEAK_HOUR)
        return self.T_MEAN_C + self.T_AMP_C * math.cos(phase)

    def step(self, hour: float, lamp_power_W: float, heater_power_W: float, biogenic_power_W: float = 0.0) -> float:
        """Advance greenhouse temperature by one simulation step (1 hour).

        Uses analytical exponential integration for unconditional numerical stability:
            dT/dt = (Q_in - UA*(T - T_ext)) / C_th
            T(t + dt) = T_eq + (T(t) - T_eq) * exp(-k * dt)
        """
        outside_C = self.outside_temp_C(hour)

        lamp_heat_W = self.LAMP_HEAT_FRACTION * max(0.0, lamp_power_W)
        heater_heat_W = self.HEATER_EFFICIENCY * max(0.0, heater_power_W)
        q_in = lamp_heat_W + heater_heat_W + biogenic_power_W

        if self.ua <= 0.0:
            delta_t = (q_in * self.TICK_SECONDS) / self.c_th
            self.temp_C += delta_t
        else:
            k = self.ua / self.c_th
            t_eq = outside_C + (q_in / self.ua)
            decay = math.exp(-k * self.TICK_SECONDS)
            self.temp_C = t_eq + (self.temp_C - t_eq) * decay

        return self.temp_C

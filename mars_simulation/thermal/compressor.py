"""In-Situ Resource Utilization (ISRU) CO2 atmospheric extraction and compressor model."""
from __future__ import annotations

import math


class MarsAtmosphereCompressorModel:
    """Thermodynamic model for compressing raw exterior Martian CO2 (~0.6 kPa)
    into the habitat CO2Store (~70-101 kPa).

    Specific Isothermal / Polytropic Compression Work:
        w_comp = (R * T * ln(P_hab / P_mars)) / eta_comp  [Joules / mole]
    """

    R_GAS = 8.314             # J / (mol * K)
    P_MARS_KPA = 0.6          # Martian barometric surface pressure (kPa)
    P_HAB_KPA = 70.0          # Habitat storage/cabin pressure (kPa)
    EFFICIENCY = 0.65         # Polytropic/mechanical compressor efficiency
    T_INTAKE_K = 223.0        # Mean intake gas temperature (K)

    def __init__(
        self,
        p_mars_kPa: float = P_MARS_KPA,
        p_hab_kPa: float = P_HAB_KPA,
        efficiency: float = EFFICIENCY,
        t_intake_K: float = T_INTAKE_K,
    ):
        self.p_mars_kPa = float(p_mars_kPa)
        self.p_hab_kPa = float(p_hab_kPa)
        self.efficiency = max(0.01, float(efficiency))
        self.t_intake_K = max(10.0, float(t_intake_K))

    def specific_work_J_per_mol(self) -> float:
        """Specific compression work in Joules per mole of CO2."""
        r_p = max(1.01, self.p_hab_kPa / max(0.01, self.p_mars_kPa))
        w_isothermal = self.R_GAS * self.t_intake_K * math.log(r_p)
        return w_isothermal / self.efficiency

    def power_required_W(self, co2_mol_per_tick: float, tick_hours: float = 1.0) -> float:
        """Electrical power (Watts) needed to compress `co2_mol_per_tick` over `tick_hours`."""
        if co2_mol_per_tick <= 0.0 or tick_hours <= 0.0:
            return 0.0
        total_joules = co2_mol_per_tick * self.specific_work_J_per_mol()
        dt_seconds = tick_hours * 3600.0
        return total_joules / dt_seconds

    def moles_compressed(self, power_available_W: float, desired_moles: float, tick_hours: float = 1.0) -> float:
        """Actual moles of CO2 compressed given available electrical power ceiling."""
        if desired_moles <= 0.0 or tick_hours <= 0.0:
            return 0.0
        power_needed = self.power_required_W(desired_moles, tick_hours)
        if power_needed <= 0.0:
            return 0.0
        fraction = min(1.0, max(0.0, power_available_W / power_needed))
        return desired_moles * fraction

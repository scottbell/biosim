"""Cross-checks the root-zone level sensor against ReservoirEstimator and picks a level to use.

Two independent readings of the same hidden quantity, so each can catch the other:
    dropout    sensor reports None
    stuck      sensor repeats the exact same value for stuck_ticks ticks (a live sensor with
               baseline noise never does; readings pinned at 0 or full don't count)
    offset     bias or drift: two-sided CUSUM on r = sensor - estimate, slack shift_L/2,
               threshold h_sigmas * the nominal spread of r. Latched until |r| has stayed
               within 2 spreads for clear_ticks ticks.
    estimator  est.degraded is set, or CO2 is below co2_min_ppm (the uptake model then
               drifts high, plant_next_steps.md item 8). The residual is not trusted then,
               so the CUSUM is reset rather than blaming the sensor.

The level reported is the inverse-variance average of whichever sources are healthy.

After a harvest (crop.biomass_store_kg goes up) the estimator settles about 2.4 L low within
~25 ticks and stays there (measured live, 2026-11-10): the replanted crop reports the old CO2
uptake. That is expected, not a fault, so for harvest_hold_ticks the estimate isn't used and
nothing is flagged. At the end of the hold the estimate is re-zeroed against the sensor: the
mean residual over the hold's last rezero_ticks becomes a standing offset on the estimate.
"""
from __future__ import annotations

from collections import deque
from typing import Any, Dict, List, Optional


class RootZoneMonitor:
    def __init__(self, sensor_sigma_L: float = 0.1, estimate_sigma_L: float = 0.31,
                 shift_L: float = 2.0, h_sigmas: float = 5.0, stuck_ticks: int = 3,
                 capacity_L: float = 500.0, move_threshold_L: float = 0.5,
                 co2_min_ppm: float = 300.0, clear_ticks: int = 24,
                 harvest_hold_ticks: int = 36, rezero_ticks: int = 12):
        self.sensor_var = float(sensor_sigma_L) ** 2
        self.estimate_var = float(estimate_sigma_L) ** 2
        self.spread = (self.sensor_var + self.estimate_var) ** 0.5
        self.k = shift_L / 2.0
        self.h = h_sigmas * self.spread
        self.stuck_ticks = int(stuck_ticks)
        self.capacity_L = float(capacity_L)
        self.move_threshold_L = float(move_threshold_L)
        self.co2_min_ppm = float(co2_min_ppm)
        self.clear_ticks = int(clear_ticks)
        self.harvest_hold_ticks = int(harvest_hold_ticks)
        self.rezero_ticks = int(rezero_ticks)
        self.reset()

    def reset(self) -> None:
        self.g_pos = 0.0
        self.g_neg = 0.0
        self.offset_alarm = False
        self._calm = 0
        self._prev_sensor: Optional[float] = None
        self._same = 0
        self._recent_est: deque = deque(maxlen=self.stuck_ticks + 1)
        self.est_offset = 0.0
        self._hold = 0
        self._hold_res: deque = deque(maxlen=self.rezero_ticks)
        self._prev_biomass: Optional[float] = None

    def _stuck(self, sensor_L: float, est_L: float) -> bool:
        self._recent_est.append(est_L)
        pinned = sensor_L <= 0.0 or sensor_L >= self.capacity_L
        self._same = self._same + 1 if (sensor_L == self._prev_sensor and not pinned) else 0
        self._prev_sensor = sensor_L
        if self._same < self.stuck_ticks - 1:
            return False
        if self.sensor_var > 0:
            return True
        # a noiseless sensor only looks stuck if the level should have moved
        return max(self._recent_est) - min(self._recent_est) > self.move_threshold_L

    def update(self, sensor_L: Optional[float], est: Dict[str, Any],
               co2_ppm: Optional[float] = None, biomass_kg: Optional[float] = None) -> Dict[str, Any]:
        if biomass_kg is not None:
            if self._prev_biomass is not None and biomass_kg > self._prev_biomass:
                self._hold = self.harvest_hold_ticks
                self._hold_res.clear()
            self._prev_biomass = biomass_kg
        post_harvest = self._hold > 0

        est_L = float(est["est.reservoir_L"]) + self.est_offset
        est_ok = not est.get("est.degraded", False) and (co2_ppm is None or co2_ppm >= self.co2_min_ppm)
        faults: List[str] = []

        if sensor_L is None:
            self._prev_sensor, self._same = None, 0
            sensor_ok, r = False, None
            faults.append("dropout")
        elif self._stuck(sensor_L, est_L):
            sensor_ok, r = False, sensor_L - est_L
            faults.append("stuck")
        else:
            sensor_ok, r = True, sensor_L - est_L

        if not est_ok or post_harvest:
            if not est_ok:
                faults.append("estimator")
            self.g_pos = self.g_neg = 0.0
            self.offset_alarm, self._calm = False, 0
            if post_harvest:
                if sensor_ok:
                    self._hold_res.append(r)
                self._hold -= 1
                if self._hold == 0 and self._hold_res:
                    self.est_offset += sum(self._hold_res) / len(self._hold_res)
            est_ok = False
        elif sensor_ok:
            self.g_pos = max(0.0, self.g_pos + r - self.k)
            self.g_neg = max(0.0, self.g_neg - r - self.k)
            if self.g_pos > self.h or self.g_neg > self.h:
                self.offset_alarm = True
            if self.offset_alarm:
                self._calm = self._calm + 1 if abs(r) < 2 * self.spread else 0
                if self._calm >= self.clear_ticks:
                    self.offset_alarm, self._calm = False, 0
                    self.g_pos = self.g_neg = 0.0
        if self.offset_alarm:
            faults.append("offset")
            sensor_ok = False

        if sensor_ok and est_ok:
            w = self.estimate_var / (self.sensor_var + self.estimate_var) if self.sensor_var + self.estimate_var else 0.5
            level, trusted = w * sensor_L + (1 - w) * est_L, "both"
        elif sensor_ok:
            level, trusted = sensor_L, "sensor"
        elif est_ok:
            level, trusted = est_L, "estimate"
        else:
            level, trusted = est_L, "none"   # nothing healthy; the estimate is the better guess

        return {
            "rz.level_L": max(0.0, level),
            "rz.trusted": trusted,
            "rz.faults": faults,
            "rz.residual_L": r,
            "rz.post_harvest": post_harvest,
            "rz.est_offset_L": self.est_offset,
        }

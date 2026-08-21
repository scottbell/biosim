package com.traclabs.biosim.server.simulation.environment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FanLawTest {

    @Test
    void fiftyWattsAtExplorationPressureIsIdealGasFlow() {
        float hourly = Fan.molesThisTick(50f, 55f, 23f + 273.15f, 1f);
        // Q_ref 36000 L/h * 55 kPa / (8.314 * 296.15 K) ≈ 804 mol/h
        assertEquals(804f, hourly, 5f);
        float molesInRoom = 55f * 18000f / (8.314f * 296.15f);
        float mixingHours = molesInRoom / hourly;
        assertTrue(mixingHours > 0.15f && mixingHours < 1.0f, "mixingHours=" + mixingHours);
    }

    @Test
    void zeroPowerMovesNoAir() {
        assertEquals(0f, Fan.molesThisTick(0f, 55f, 296.15f, 1f / 60f));
    }
}

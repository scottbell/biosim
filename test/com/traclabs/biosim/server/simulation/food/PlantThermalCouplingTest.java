package com.traclabs.biosim.server.simulation.food;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlantThermalCouplingTest {

    @Test
    public void testThermalEfficiencyCurve() {
        // 1. Peak efficiency at optimal 22 °C
        assertEquals(1.0f, Plant.calculateThermalEfficiency(22.0f), 0.001f, "Optimal 22°C should give eta_T = 1.0");

        // 2. Freezing limit 0 °C
        assertEquals(0.0f, Plant.calculateThermalEfficiency(0.0f), 0.001f, "Freezing 0°C should give eta_T = 0.0");
        assertEquals(0.0f, Plant.calculateThermalEfficiency(-5.0f), 0.001f, "Below freezing should give eta_T = 0.0");

        // 3. Upper heat limit 35 °C
        assertEquals(0.0f, Plant.calculateThermalEfficiency(35.0f), 0.001f, "Heat limit 35°C should give eta_T = 0.0");
        assertEquals(0.0f, Plant.calculateThermalEfficiency(40.0f), 0.001f, "Above heat limit should give eta_T = 0.0");

        // 4. Intermediate temperatures
        float eta15 = Plant.calculateThermalEfficiency(15.0f);
        assertTrue(eta15 > 0.0f && eta15 < 1.0f, "15°C should give partial efficiency");

        float eta28 = Plant.calculateThermalEfficiency(28.0f);
        assertTrue(eta28 > 0.0f && eta28 < 1.0f, "28°C should give partial efficiency");
    }
}

package com.traclabs.biosim.server.simulation.environment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimEnvironmentTemperatureTest {

    @Test
    public void testTemperatureSetting() {
        SimEnvironment env = new SimEnvironment(1, 50000, "Greenhouse");
        assertEquals(22.0f, env.getTemperature(), 0.01f);
        assertEquals(295.15f, env.getTemperatureInKelvin(), 0.01f);

        env.setTemperature(15.5f);
        assertEquals(15.5f, env.getTemperature(), 0.01f);
        assertEquals(288.65f, env.getTemperatureInKelvin(), 0.01f);

        env.reset();
        assertEquals(22.0f, env.getTemperature(), 0.01f);
    }
}

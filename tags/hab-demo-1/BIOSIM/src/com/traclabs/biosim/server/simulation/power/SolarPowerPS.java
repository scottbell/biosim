package com.traclabs.biosim.server.simulation.power;

import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;

/**
 * Solar Power Production System
 * 
 * @author Scott Bell
 */

public class SolarPowerPS extends PowerPSImpl {

    public SolarPowerPS(int pID, String pName) {
        super(pID, pName);
    }

    protected float calculatePowerProduced() {
        SimEnvironment lightInput = getLightConsumerDefinition()
                .getEnvironments()[0];
        //Varying stream of power
        if (lightInput != null) {
            float powerGenerated = getTickLength() * getCurrentUpperPowerGeneration()
                    * (lightInput.getLightIntensity() / lightInput
                            .getMaxLumens());
            return powerGenerated;
        }
		myLogger.error("SolarPowerPS: no light input!");
		return 0;
    }

}
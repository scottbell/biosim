package com.traclabs.biosim.server.simulation.power;

/**
 * Solar Power Production System
 * 
 * @author Scott Bell
 */

public class SolarPowerPS extends PowerPSImpl {

    public SolarPowerPS(int pID, String pName) {
        super(pID, pName);
    }

    float calculatePowerProduced() {
        //Varying stream of power
        if (getLightInput() != null) {
            float powerGenerated = getCurrentUpperPowerGeneration()
                    * (getLightInput().getLightIntensity() / getLightInput()
                            .getMaxLumens());
            return randomFilter(powerGenerated);
        } else {
            myLogger.error("SolarPowerPS: no light input!");
            return randomFilter(0);
        }
    }

}
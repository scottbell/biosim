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
        if (getLightInput() != null)
            return randomFilter(new Double(
                    getLightInput().getLightIntensity() * .09).floatValue());
        else {
            myLogger.error("SolarPowerPS: no light input!");
            return randomFilter(0);
        }
    }

}
package com.traclabs.biosim.server.simulation.power;

/**
 * Nuclear Power Production System
 * 
 * @author Scott Bell
 */

public class NuclearPowerPS extends PowerPSImpl {
    public NuclearPowerPS(int pID, String pName) {
        super(pID, pName);
    }

    protected float calculatePowerProduced() {
        //Constant steady stream of power
        return getTickLength() * 1000000f;
    }

}
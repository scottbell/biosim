package com.traclabs.biosim.server.simulation.air;

/**
 * CH4Tank Subsystem
 * 
 * @author Scott Bell
 */

public class CH4Tank extends AirRSSubSystem {
    private float CH4Level = 0;

    public CH4Tank(AirRSImpl pAirRSImpl) {
        super(pAirRSImpl);
        hasEnoughPower = true;
    }

    public void addCH4(float molesToAdd) {
        molesToAdd = myAirRS.randomFilter(molesToAdd);
        CH4Level += molesToAdd;
    }

    public void reset() {
        currentPowerConsumed = 0;
        hasEnoughPower = true;
        CH4Level = 0;
    }

    public void tick() {
    }

}
package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;

/**
 * The AES is the third stage of water purification. It takes water from the RO,
 * filters it some, and sends the water to the PPS
 * 
 * @author Scott Bell
 */

public class AES extends WaterRSSubSystem {
    private float currentPPSWaterProduced = 0f;

    /**
     * Constructor that creates the AES
     * 
     * @param pWaterRSImpl
     *            The Water RS system the AES is contained in
     */
    public AES(WaterRSImpl pWaterRSImpl) {
        super(pWaterRSImpl);
        currentPowerNeeded = basePowerNeeded = 168;
    }

    public float getPPSWaterProduced() {
        return currentPPSWaterProduced;
    }

    public void log() {
        super.log();
        myLogger.debug("currentPPSWaterProduced=" + currentPPSWaterProduced);
    }

    /**
     * Flushes the water from this subsystem to the PPS
     */
    private void pushWater() {
        //if PPS is enabled, give it to it
        if (myWaterRS.getPPS().isEnabled()) {
            currentPPSWaterProduced = waterLevel;
            myWaterRS.getPPS().addWater(currentPPSWaterProduced);
            waterLevel = 0;
        }
        //otherwise, push it to the grey water tank
        else {
            waterLevel = SimBioModuleImpl.pushResourceToStore(myWaterRS
                    .getGreyWaterInputs(), myWaterRS
                    .getGreyWaterInputMaxFlowRates(), myWaterRS
                    .getGreyWaterInputDesiredFlowRates(), myWaterRS
                    .getGreyWaterInputActualFlowRates(), waterLevel);
        }
    }

    /**
     * In one tick, this subsystem: 1) Collects references (if needed). 2)
     * Flushes the water from this subsystem to the PPS.
     */
    public void tick() {
        super.tick();
        if (enabled)
            if (hasEnoughPower)
                pushWater();
            else {
                currentPPSWaterProduced = 0f;
                //dump extra water
                waterLevel = 0f;

            }
        else {
            currentPPSWaterProduced = 0f;
            //try to put back into dirtyWater Store.
            waterLevel = SimBioModuleImpl.pushResourceToStore(myWaterRS
                    .getDirtyWaterInputs(), myWaterRS
                    .getDirtyWaterInputMaxFlowRates(), myWaterRS
                    .getDirtyWaterInputDesiredFlowRates(), myWaterRS
                    .getDirtyWaterInputActualFlowRates(), waterLevel);
        }
    }

    public void reset() {
        super.reset();
        currentPPSWaterProduced = 0f;
    }
}
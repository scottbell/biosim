package com.traclabs.biosim.server.simulation.water.aws;

import com.traclabs.biosim.server.simulation.water.WaterRS;

/**
 * The RO is the second stage of water purification. It takes water from the
 * BWP, filters it some, and sends the water to the AES
 *
 * @author Scott Bell
 */

public class RO extends WaterRSSubSystem {
    private float currentAESWaterProduced = 0f;

    private float currentPPSWaterProduced = 0f;

    /**
     * Constructor that creates the RO
     *
     * @param pWaterRS The Water RS system the RO is contained in
     */
    public RO(WaterRS pWaterRS) {
        super(pWaterRS);
    }

    public void log() {
        super.log();
        myLogger.debug("currentAESWaterProduced=" + currentAESWaterProduced);
        myLogger.debug("currentPPSWaterProduced=" + currentPPSWaterProduced);
    }

    public float getAESWaterProduced() {
        return currentAESWaterProduced;
    }

    public float getPPSWaterProduced() {
        return currentPPSWaterProduced;
    }

    /**
     * Flushes the water from this subsystem to the AES
     */
    private void pushWater() {
        //if AES is enabled, send 15% of water to it
        if (myWaterRS.getAES().isEnabled()) {
            currentAESWaterProduced = (Double.valueOf(waterLevel * 0.15f))
                    .floatValue();
            myWaterRS.getAES().addWater(currentAESWaterProduced);
        }
        //otherwise send it to dirty water tank
        else {
            currentAESWaterProduced = 0f;
        }
        //if PPS is enabled, give it to it
        if (myWaterRS.getPPS().isEnabled()) {
            currentPPSWaterProduced = (Double.valueOf(waterLevel * 0.85f))
                    .floatValue();
            myWaterRS.getPPS().addWater(currentPPSWaterProduced);
            waterLevel = 0;
        }
        //if not, send it to grey water tank
        else {
            waterLevel = myWaterRS.getGreyWaterConsumerDefinition()
                    .pushResourceToStores(waterLevel);
        }
    }

    /**
     * In one tick, this subsystem: 1) Collects references (if needed). 2)
     * Flushes the water from this subsystem to the AES.
     */
    public void tick() {
        super.tick();
        if (enabled) {
            if (hasEnoughPower) {
                pushWater();
            } else {
                //try to put back into dirtyWater Store.
                waterLevel = myWaterRS.getDirtyWaterConsumerDefinition()
                        .pushResourceToStores(waterLevel);
                //dump extra water
                waterLevel = 0f;
                currentAESWaterProduced = 0f;
                currentPPSWaterProduced = 0f;
            }
        } else {
            //try to put back into dirtyWater Store.
            waterLevel = myWaterRS.getDirtyWaterConsumerDefinition()
                    .pushResourceToStores(waterLevel);
            //dump extra water
            waterLevel = 0f;
            currentAESWaterProduced = 0f;
            currentPPSWaterProduced = 0f;
        }
    }

    public void reset() {
        super.reset();
        currentAESWaterProduced = 0f;
        currentPPSWaterProduced = 0f;
    }
}
package com.traclabs.biosim.server.simulation.water.aws;

import com.traclabs.biosim.server.simulation.water.WaterRS;

/**
 * The Biological Waste Processor is the first stage of water purification. It
 * takes dirty/grey water, filters it some, and sends the water to the RO
 * 
 * @author Scott Bell
 */

public class BWP extends WaterRSSubSystem {
    //The subsystem to send the water to next
    private float currentDirtyWaterConsumed = 0f;

    private float currentGreyWaterConsumed = 0f;

    private float currentROWaterProduced = 0f;

    private float currentAESWaterProduced = 0f;

    private final float NORMAL_WATER_NEEDED = waterNeeded;

    private final float RO_DISABLED_WATER_NEEDED = Double.valueOf(
            waterNeeded * 0.15f).floatValue();

    private final static float BOTH_DISABLED_WATER_NEEDED = 0f;

    /**
     * Constructor that creates the BWP
     * 
     * @param pWaterRS
     *            The Water RS system the BWP is contained in
     */
    public BWP(WaterRS pWaterRS) {
        super(pWaterRS);
    }

    public void log() {
        super.log();
        myLogger
                .debug("currentDirtyWaterConsumed=" + currentDirtyWaterConsumed);
        myLogger.debug("currentGreyWaterConsumed=" + currentGreyWaterConsumed);
        myLogger.debug("currentROWaterProduced=" + currentROWaterProduced);
        myLogger.debug("currentAESWaterProduced=" + currentAESWaterProduced);
        myLogger.debug("NORMAL_WATER_NEEDED=" + NORMAL_WATER_NEEDED);
    }

    public float getDirtyWaterConsumed() {
        return currentDirtyWaterConsumed;
    }

    public float getGreyWaterConsumed() {
        return currentGreyWaterConsumed;
    }

    /**
     * Attempts to collect enough water from the Dirty Water Store to put into
     * the BWP. If the Dirty Water Store can't provide enough, the Water RS
     * supplements from the Grey Water Store.
     */
    private void gatherWater() {
        if (!myWaterRS.getRO().isEnabled()) {
            if (myWaterRS.getAES().isEnabled())
                waterNeeded = RO_DISABLED_WATER_NEEDED;
            else
                waterNeeded = BOTH_DISABLED_WATER_NEEDED;
        } else {
            waterNeeded = NORMAL_WATER_NEEDED;
        }
        waterNeeded *= myWaterRS.getTickLength();
        currentDirtyWaterConsumed = myWaterRS
                .getDirtyWaterConsumerDefinition().getResourceFromStores(
                        waterNeeded);
        currentGreyWaterConsumed = myWaterRS
                .getGreyWaterConsumerDefinition().getResourceFromStores(
                        waterNeeded - currentDirtyWaterConsumed);
        float gatheredWater = currentDirtyWaterConsumed
                + currentGreyWaterConsumed;
        if (gatheredWater < waterNeeded) {
            hasEnoughWater = false;
        } else {
            hasEnoughWater = true;
        }
        addWater(currentDirtyWaterConsumed + currentGreyWaterConsumed);
    }

    /**
     * Flushes the water from this subsystem to the RO
     */
    private void pushWater() {
        if (myWaterRS.getRO().isEnabled()) {
            currentROWaterProduced = waterLevel;
            currentAESWaterProduced = 0;
            myWaterRS.getRO().addWater(currentROWaterProduced);
        } else if (myWaterRS.getAES().isEnabled()) {
            currentROWaterProduced = 0;
            currentAESWaterProduced = waterLevel;
            myWaterRS.getAES().addWater(currentAESWaterProduced);
        } else {
            //dump water back to dirty water store
            waterLevel = myWaterRS.getDirtyWaterConsumerDefinition()
                    .pushResourceToStores(waterLevel);
            //dump rest
            waterLevel = 0f;
            currentAESWaterProduced = 0f;
            currentROWaterProduced = 0f;
        }
        waterLevel = 0;
    }

    public float getROWaterProduced() {
        return currentROWaterProduced;
    }

    public float getAESWaterProduced() {
        return currentAESWaterProduced;
    }

    /**
     * In one tick, this subsystem: 1) Collects references (if needed). 2)
     * Flushes the water from this subsystem to the RO.
     */
    public void tick() {
        super.tick();
        if (hasEnoughPower) {
            gatherWater();
            pushWater();
        } else {
            currentROWaterProduced = 0f;
            currentAESWaterProduced = 0f;
            currentDirtyWaterConsumed = 0f;
            currentGreyWaterConsumed = 0f;
        }
    }

    public void reset() {
        super.reset();
        currentDirtyWaterConsumed = 0f;
        currentGreyWaterConsumed = 0f;
        currentROWaterProduced = 0f;
        currentAESWaterProduced = 0f;
    }
}
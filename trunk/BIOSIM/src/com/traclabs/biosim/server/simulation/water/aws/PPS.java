package com.traclabs.biosim.server.simulation.water.aws;

import com.traclabs.biosim.server.simulation.water.WaterRSImpl;

/**
 * The PPS is the last stage of water purification. It takes water from the AES,
 * filters it, and waits for the WaterRS to send the now clean water to the
 * potable water store
 * 
 * @author Scott Bell
 */

public class PPS extends WaterRSSubSystem {
    private float potableWaterProduced = 0f;

    /**
     * Constructor that creates the PPS, the power required is 168 watts
     * 
     * @param pWaterRSImpl
     *            The Water RS system the AES is contained in
     */
    public PPS(WaterRSImpl pWaterRSImpl) {
        super(pWaterRSImpl);
    }

    /**
     * Flushes the water from this subsystem (via the WaterRS) to the Potable
     * Water Store
     */
    private void pushWater() {
        potableWaterProduced = waterLevel;
        waterLevel = 0;
        myWaterRS
                .getPotableWaterProducerDefinitionImpl().pushResourceToStores(
                        potableWaterProduced);
    }

    public float getPotableWaterProduced() {
        return potableWaterProduced;
    }

    /**
     * In one tick, this subsystem: 1) Collects references (if needed). 2)
     * Flushes the water from this subsystem to the RO.
     */
    public void tick() {
        super.tick();
        if (hasEnoughPower)
            pushWater();
        else
            potableWaterProduced = 0f;
    }

    public void log() {
        super.log();
        myLogger.debug("potableWaterProduced=" + potableWaterProduced);
    }

    public void reset() {
        super.reset();
        potableWaterProduced = 0f;
    }
}
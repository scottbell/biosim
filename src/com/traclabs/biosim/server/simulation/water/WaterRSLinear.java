package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.server.framework.Malfunction;
import com.traclabs.biosim.server.framework.MalfunctionIntensity;
import com.traclabs.biosim.server.framework.MalfunctionLength;

/**
 * The Water Recovery System takes grey/dirty water and refines it to potable
 * water for the crew members and grey water for the crops.. Class modeled after
 * the paper:. "Intelligent Control of a Water Recovery System: Three Years in
 * the Trenches" by Bonasso, Kortenkamp, and Thronesbery
 *
 * @author Scott Bell
 */

public class WaterRSLinear extends AbstractWaterRS {
    private float currentPowerConsumed = 0f;

    private float currentWaterConsumed = 0f;

    /**
     * Creates the Water RS and it's subsystems
     */
    public WaterRSLinear(int pID, String pName) {
        super(pID, pName);
    }

    /**
     * Resets production/consumption levels and resets all the subsystems
     */
    public void reset() {
        super.reset();
        currentPowerConsumed = 0f;
    }

    private void gatherPower() {
        currentPowerConsumed = myPowerConsumerDefinition
                .getMostResourceFromStores();
    }

    private void gatherWater() {
        //1540 Watts -> 4.26 liters of water
        float waterNeeded = (currentPowerConsumed / 1540f) * 4.26f * getTickLength();
        float currentDirtyWaterConsumed = myDirtyWaterConsumerDefinition
                .getResourceFromStores(waterNeeded);
        float currentGreyWaterConsumed = myGreyWaterConsumerDefinition
                .getResourceFromStores(waterNeeded - currentDirtyWaterConsumed);
        currentWaterConsumed = currentDirtyWaterConsumed
                + currentGreyWaterConsumed;
    }

    /**
     * Flushes the water from this subsystem (via the WaterRS) to the Potable
     * Water Store
     */
    private void pushWater() {
        myPotableWaterProducerDefinition
                .pushResourceToStores(currentWaterConsumed);
    }

    /**
     * When ticked, the Water RS: 1) gets as much water as it can in relation to
     * power
     */
    public void tick() {
        super.tick();
        gatherPower();
        gatherWater();
        pushWater();
    }

    protected String getMalfunctionName(MalfunctionIntensity pIntensity,
                                        MalfunctionLength pLength) {
        return "Broken";
    }

    public void log() {
        myLogger.debug("power_consumed=" + currentPowerConsumed);
    }


    @Override
    protected void performMalfunctions() {
        for (Malfunction malfunction : myMalfunctions.values()) {
            malfunction.setPerformed(true);
        }
        if (myMalfunctions.size() > 0) {
            myPowerConsumerDefinition.malfunction();
            myGreyWaterConsumerDefinition.malfunction();
            myDirtyWaterConsumerDefinition.malfunction();
            myPotableWaterProducerDefinition.malfunction();
        }
    }

}
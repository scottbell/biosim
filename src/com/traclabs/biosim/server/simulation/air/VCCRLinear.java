package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.framework.Malfunction;
import com.traclabs.biosim.server.framework.MalfunctionIntensity;
import com.traclabs.biosim.server.simulation.environment.Air;

/**
 * Produces air with less CO2.
 *
 * @author Scott Bell
 */

public class VCCRLinear extends AbstractVCCR {
    private float currentCO2Produced = 0f;

    private float currentPowerConsumed = 0;

    private float gatheredCO2 = 0;
    
    // 1.0 = fully operational, 0.0 = completely non-operational
    private float operationalEfficiency = 1.0f;

    public VCCRLinear(int pID, String pName) {
        super(pID, pName);
    }

    /**
     * Processes a tick by collecting referernces (if needed), resources, and
     * pushing the new air out.
     */
    public void tick() {
        super.tick();
        gatherPower();
        gatherCO2();
        pushCO2();
    }

    /**
     * Adds power for this tick
     */
    private void gatherPower() {
        float requestedPower = myPowerConsumerDefinition.getDesiredFlowRate(0) * operationalEfficiency;
        currentPowerConsumed = myPowerConsumerDefinition.getResourceFromStores(requestedPower);
    }
    
    @Override
    protected void performMalfunctions() {
        if (myMalfunctions.size() > 0) {
            // Default to fully operational
            operationalEfficiency = 1.0f;
            
            // Get the most severe malfunction
            MalfunctionIntensity worstIntensity = MalfunctionIntensity.LOW_MALF;
            for (Malfunction malfunction : myMalfunctions.values()) {
                if (malfunction.getIntensity().ordinal() > worstIntensity.ordinal()) {
                    worstIntensity = malfunction.getIntensity();
                }
                // Mark as performed
                if (!malfunction.hasPerformed()) {
                    myLogger.debug("Performing malfunction: " + malfunction);
                    malfunction.setPerformed(true);
                }
            }
            
            // Apply the appropriate reduction based on intensity
            if (worstIntensity == MalfunctionIntensity.LOW_MALF) {
                // 25% reduction in efficiency
                operationalEfficiency = 0.75f;
            } else if (worstIntensity == MalfunctionIntensity.MEDIUM_MALF) {
                // 75% reduction in efficiency
                operationalEfficiency = 0.25f;
            } else if (worstIntensity == MalfunctionIntensity.SEVERE_MALF) {
                // 100% reduction in efficiency (complete failure)
                operationalEfficiency = 0.0f;
            }
        } else {
            // No malfunctions, fully operational
            operationalEfficiency = 1.0f;
        }
    }

    /**
     * Resets production/consumption levels.
     */
    public void reset() {
        super.reset();
        currentPowerConsumed = 0;
        currentCO2Produced = 0f;
        operationalEfficiency = 1.0f;
    }

    private void gatherCO2() {
        //25.625 watts -> 1.2125 moles of air per tick
        //roughly 0.02844 moles of CO2 per tick
        float molesAirNeeded = (currentPowerConsumed / 25.625f) * 1.2125f * 100 * getTickLength();
        Air airConsumed = myAirConsumerDefinition.getAirFromEnvironment(molesAirNeeded, 0);
        gatheredCO2 = airConsumed.co2Moles;
        airConsumed.co2Moles = 0f;
        myAirProducerDefinition.pushAirToEnvironment(airConsumed, 0);
    }

    public void log() {
        myLogger.debug("power_consumed=" + currentPowerConsumed);
        myLogger.debug("operational_efficiency=" + operationalEfficiency);
    }

    private void pushCO2() {
        //distrbute CO2
        currentCO2Produced = gatheredCO2;
        myCO2ProducerDefinition
                .pushResourceToStores(currentCO2Produced);
    }
}

package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.WaterAirConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.water.WaterStore;

/**
 * @author Scott Bell
 */

public class WaterAirConsumerDefinitionImpl extends StoreEnvironmentFlowRateControllableImpl implements WaterAirConsumerDefinitionOperations {
    
    public void setWaterAirEnvironmentInputs(SimEnvironment[] pEnvironments, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setEnvironments(pEnvironments);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }

    public void setWaterAirStoreInputs(WaterStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
    
    /**
     * Grabs as much Water as it can (i.e., the maxFlowRate) from environments.
     * @return The total amount of Water grabbed from the environments
     */
    public float getMostWaterFromEnvironment() {
        float gatheredWaterAir = 0f;
        for (int i = 0; i < getEnvironments().length; i++) {
            float amountToTake = Math.min(getEnvironmentMaxFlowRate(i),
                    getEnvironmentDesiredFlowRate(i));
            getEnvironmentActualFlowRates()[i] = getEnvironments()[i]
                    .takeWaterMoles(amountToTake);
            gatheredWaterAir += getEnvironmentActualFlowRate(i);
        }
        return gatheredWaterAir;
    }

}
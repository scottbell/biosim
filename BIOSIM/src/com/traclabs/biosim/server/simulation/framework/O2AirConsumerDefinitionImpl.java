package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.air.O2Store;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.O2AirConsumerDefinitionOperations;

/**
 * @author Scott Bell
 */

public class O2AirConsumerDefinitionImpl extends StoreEnvironmentFlowRateControllableImpl implements O2AirConsumerDefinitionOperations {
    
    public void setO2AirEnvironmentInputs(SimEnvironment[] pEnvironments, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setEnvironments(pEnvironments);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }

    public void setO2AirStoreInputs(O2Store[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
    
    /**
     * Grabs as much O2 as it can (i.e., the maxFlowRate) from environments.
     * @return The total amount of O2 grabbed from the environments
     */
    public float getMostO2FromEnvironment() {
        float gatheredO2Air = 0f;
        for (int i = 0; i < getEnvironments().length; i++) {
            float amountToTake = Math.min(getEnvironmentMaxFlowRate(i),
                    getEnvironmentDesiredFlowRate(i));
            getEnvironmentActualFlowRates()[i] = getEnvironments()[i]
                    .takeO2Moles(amountToTake);
            gatheredO2Air += getEnvironmentActualFlowRate(i);
        }
        return gatheredO2Air;
    }

}
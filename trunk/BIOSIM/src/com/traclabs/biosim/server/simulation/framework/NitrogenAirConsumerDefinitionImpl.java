package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.air.NitrogenStore;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.NitrogenAirConsumerDefinitionOperations;

/**
 * @author Scott Bell
 */

public class NitrogenAirConsumerDefinitionImpl extends StoreEnvironmentFlowRateControllableImpl implements NitrogenAirConsumerDefinitionOperations {
    
    public void setNitrogenAirEnvironmentInputs(SimEnvironment[] pEnvironments, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setEnvironments(pEnvironments);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }

    public void setNitrogenAirStoreInputs(NitrogenStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
    
    /**
     * Grabs as much Nitrogen as it can (i.e., the maxFlowRate) from environments.
     * @return The total amount of Nitrogen grabbed from the environments
     */
    public float getMostNitrogenFromEnvironment() {
        float gatheredNitrogenAir = 0f;
        for (int i = 0; i < getEnvironments().length; i++) {
            float amountToTake = Math.min(getEnvironmentMaxFlowRate(i),
                    getEnvironmentDesiredFlowRate(i));
            getEnvironmentActualFlowRates()[i] = getEnvironments()[i]
                    .takeNitrogenMoles(amountToTake);
            gatheredNitrogenAir += getEnvironmentActualFlowRate(i);
        }
        return gatheredNitrogenAir;
    }
    
}
package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.WaterAirProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.water.WaterStore;

/**
 * @author Scott Bell
 */

public class WaterAirProducerDefinitionImpl extends StoreEnvironmentFlowRateControllableImpl implements WaterAirProducerDefinitionOperations {
    
    public void setWaterAirEnvironmentOutputs(SimEnvironment[] pEnvironments, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setEnvironments(pEnvironments);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }

    public void setWaterAirStoreOutputs(WaterStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
    
    /**
     * @return The total amount of Water pushed to the environments
     */
    public float pushWaterToEnvironment(float pMolesToPush) {
        float WaterAirLeft = pMolesToPush;
        for (int i = 0; (i < getEnvironments().length)
                && (WaterAirLeft > 0); i++) {
            float amountToPushFirst = Math.min(
                    getEnvironmentMaxFlowRate(i),
                    getEnvironmentDesiredFlowRate(i));
            float amountToPushFinal = Math.min(amountToPushFirst, WaterAirLeft);
            getEnvironmentActualFlowRates()[i] = getEnvironments()[i]
                    .addWaterMoles(amountToPushFinal);
            WaterAirLeft -= getEnvironmentActualFlowRate(i);
        }
        return pMolesToPush - WaterAirLeft;
    }

}
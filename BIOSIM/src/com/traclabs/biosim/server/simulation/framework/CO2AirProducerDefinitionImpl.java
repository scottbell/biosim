package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.air.CO2Store;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.CO2AirProducerDefinitionOperations;

/**
 * @author Scott Bell
 */

public class CO2AirProducerDefinitionImpl extends
        StoreEnvironmentFlowRateControllableImpl implements
        CO2AirProducerDefinitionOperations {

    public void setCO2AirEnvironmentOutputs(SimEnvironment[] pEnvironments,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setEnvironments(pEnvironments);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }

    public void setCO2AirStoreOutputs(CO2Store[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }

    /**
     * @return The total amount of CO2 pushed to the environments
     */
    public float pushCO2ToEnvironment(float pMolesToPush) {
        float CO2AirLeft = pMolesToPush;
        for (int i = 0; (i < getEnvironments().length)
                && (CO2AirLeft > 0); i++) {
            float amountToPushFirst = Math.min(
                    getEnvironmentMaxFlowRate(i),
                    getEnvironmentDesiredFlowRate(i));
            float amountToPushFinal = Math.min(amountToPushFirst, CO2AirLeft);
            getEnvironmentActualFlowRates()[i] = getEnvironments()[i]
                    .addCO2Moles(amountToPushFinal);
            CO2AirLeft -= getEnvironmentActualFlowRate(i);
        }
        return pMolesToPush - CO2AirLeft;
    }

}
package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.air.O2Store;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.O2AirProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.framework.O2AirProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.O2AirProducerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.framework.O2AirProducerDefinitionPOATie;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class O2AirProducerDefinitionImpl extends StoreEnvironmentFlowRateControllableImpl implements O2AirProducerDefinitionOperations {
    private O2AirProducerDefinition myO2AirProducerDefinition;
    
    public O2AirProducerDefinitionImpl(){
        myO2AirProducerDefinition = O2AirProducerDefinitionHelper.narrow(OrbUtils.poaToCorbaObj(new O2AirProducerDefinitionPOATie(this)));
    }
    
    public O2AirProducerDefinition getCorbaObject(){
        return myO2AirProducerDefinition;
    }
    
    public void setO2AirEnvironmentOutputs(SimEnvironment[] pEnvironments, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setEnvironments(pEnvironments);
        setEnvironmentMaxFlowRates(pMaxFlowRates);
        setEnvironmentDesiredFlowRates(pDesiredFlowRates);
    }

    public void setO2AirStoreOutputs(O2Store[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setStoreMaxFlowRates(pMaxFlowRates);
        setStoreDesiredFlowRates(pDesiredFlowRates);
    }
    
    /**
     * @return The total amount of O2 pushed to the environments
     */
    public float pushO2ToEnvironment(float pMolesToPush) {
        if (getEnvironments() == null)
            return 0f;
        float O2AirLeft = pMolesToPush;
        for (int i = 0; (i < getEnvironments().length)
                && (O2AirLeft > 0); i++) {
            float amountToPushFirst = Math.min(
                    getEnvironmentMaxFlowRate(i),
                    getEnvironmentDesiredFlowRate(i));
            float amountToPushFinal = Math.min(amountToPushFirst, O2AirLeft);
            getEnvironmentActualFlowRates()[i] = getEnvironments()[i]
                    .addO2Moles(amountToPushFinal);
            O2AirLeft -= getEnvironmentActualFlowRate(i);
        }
        return pMolesToPush - O2AirLeft;
    }
    
}
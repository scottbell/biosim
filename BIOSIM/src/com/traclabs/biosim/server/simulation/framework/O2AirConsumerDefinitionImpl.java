package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.air.O2Store;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.O2AirConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.framework.O2AirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.O2AirConsumerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.framework.O2AirConsumerDefinitionPOATie;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class O2AirConsumerDefinitionImpl extends StoreEnvironmentFlowRateControllableImpl implements O2AirConsumerDefinitionOperations {
    private O2AirConsumerDefinition myO2AirConsumerDefinition;
    
    public O2AirConsumerDefinitionImpl(){
        myO2AirConsumerDefinition = O2AirConsumerDefinitionHelper.narrow(OrbUtils.poaToCorbaObj(new O2AirConsumerDefinitionPOATie(this)));
    }
    
    public O2AirConsumerDefinition getCorbaObject(){
        return myO2AirConsumerDefinition;
    }
    
    public void setO2AirEnvironmentInputs(SimEnvironment[] pEnvironments, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setEnvironments(pEnvironments);
        setEnvironmentMaxFlowRates(pMaxFlowRates);
        setEnvironmentDesiredFlowRates(pDesiredFlowRates);
    }

    public void setO2AirStoreInputs(O2Store[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setStoreMaxFlowRates(pMaxFlowRates);
        setStoreDesiredFlowRates(pDesiredFlowRates);
    }
    
    /**
     * Grabs as much O2 as it can (i.e., the maxFlowRate) from environments.
     * @return The total amount of O2 grabbed from the environments
     */
    public float getMostO2FromEnvironment() {
        if (getEnvironments() == null)
            return 0f;
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
package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.air.CO2Store;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.CO2AirConsumerDefinitionOperations;

/**
 * @author Scott Bell
 */

public class CO2AirConsumerDefinitionImpl extends StoreEnvironmentFlowRateControllableImpl implements CO2AirConsumerDefinitionOperations {
    
    public void setCO2AirEnvironmentInputs(SimEnvironment[] pEnvironments, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setEnvironments(pEnvironments);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }

    public void setCO2AirStoreInputs(CO2Store[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }

}
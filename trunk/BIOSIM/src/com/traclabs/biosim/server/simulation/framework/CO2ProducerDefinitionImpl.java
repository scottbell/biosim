package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.air.CO2Store;
import com.traclabs.biosim.idl.simulation.framework.CO2ProducerDefinitionOperations;

/**
 * @author Scott Bell
 */

public class CO2ProducerDefinitionImpl extends StoreFlowRateControllableImpl implements CO2ProducerDefinitionOperations {
    public void setCO2Outputs(CO2Store[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
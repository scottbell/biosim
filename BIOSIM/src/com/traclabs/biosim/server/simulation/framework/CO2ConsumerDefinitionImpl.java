package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.air.CO2Store;
import com.traclabs.biosim.idl.simulation.framework.CO2ConsumerDefinitionOperations;

/**
 * @author Scott Bell
 */

public class CO2ConsumerDefinitionImpl extends StoreFlowRateControllableImpl implements CO2ConsumerDefinitionOperations {
    public void setCO2Inputs(CO2Store[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.air.O2Store;
import com.traclabs.biosim.idl.simulation.framework.O2ConsumerDefinitionOperations;

/**
 * @author Scott Bell
 */

public class O2ConsumerDefinitionImpl extends StoreFlowRateControllableImpl implements O2ConsumerDefinitionOperations {
    public void setO2Inputs(O2Store[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.air.H2Store;
import com.traclabs.biosim.idl.simulation.framework.H2ConsumerDefinitionOperations;

/**
 * @author Scott Bell
 */

public class H2ConsumerDefinitionImpl extends StoreFlowRateControllableImpl implements H2ConsumerDefinitionOperations {
    public void setH2Inputs(H2Store[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
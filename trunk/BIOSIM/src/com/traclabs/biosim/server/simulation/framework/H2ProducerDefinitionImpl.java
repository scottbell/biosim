package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.air.H2Store;
import com.traclabs.biosim.idl.simulation.framework.H2ProducerDefinitionOperations;

/**
 * @author Scott Bell
 */

public class H2ProducerDefinitionImpl extends StoreFlowRateControllableImpl implements H2ProducerDefinitionOperations {
    public void setH2Outputs(H2Store[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
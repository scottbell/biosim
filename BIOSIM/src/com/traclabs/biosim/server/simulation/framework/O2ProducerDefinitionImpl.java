package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.air.O2Store;
import com.traclabs.biosim.idl.simulation.framework.O2ProducerDefinitionOperations;

/**
 * @author Scott Bell
 */

public class O2ProducerDefinitionImpl extends StoreFlowRateControllableImpl implements O2ProducerDefinitionOperations {
    public void setO2Outputs(O2Store[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
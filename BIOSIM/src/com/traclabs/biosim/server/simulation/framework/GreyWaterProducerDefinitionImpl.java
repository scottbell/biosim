package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.framework.GreyWaterProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.water.GreyWaterStore;

/**
 * @author Scott Bell
 */

public class GreyWaterProducerDefinitionImpl extends StoreFlowRateControllableImpl implements GreyWaterProducerDefinitionOperations {
    public void setGreyWaterOutputs(GreyWaterStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
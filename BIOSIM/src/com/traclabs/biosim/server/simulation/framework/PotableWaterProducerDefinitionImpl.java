package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.framework.PotableWaterProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStore;

/**
 * @author Scott Bell
 */

public class PotableWaterProducerDefinitionImpl extends StoreFlowRateControllableImpl implements PotableWaterProducerDefinitionOperations {
    public void setPotableWaterOutputs(PotableWaterStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
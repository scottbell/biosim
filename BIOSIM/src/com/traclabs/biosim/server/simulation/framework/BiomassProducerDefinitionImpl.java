package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.food.BiomassStore;
import com.traclabs.biosim.idl.simulation.framework.BiomassProducerDefinitionOperations;

/**
 * @author Scott Bell
 */

public class BiomassProducerDefinitionImpl extends StoreFlowRateControllableImpl implements BiomassProducerDefinitionOperations {
    public void setBiomassOutputs(BiomassStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
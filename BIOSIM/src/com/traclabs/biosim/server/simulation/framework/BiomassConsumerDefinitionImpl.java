package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.food.BiomassStore;
import com.traclabs.biosim.idl.simulation.framework.BiomassConsumerDefinitionOperations;

/**
 * @author Scott Bell
 */

public class BiomassConsumerDefinitionImpl extends StoreFlowRateControllableImpl implements BiomassConsumerDefinitionOperations {
    public void setBiomassInputs(BiomassStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.food.FoodStore;
import com.traclabs.biosim.idl.simulation.framework.FoodProducerDefinitionOperations;

/**
 * @author Scott Bell
 */

public class FoodProducerDefinitionImpl extends StoreFlowRateControllableImpl implements FoodProducerDefinitionOperations {
    public void setFoodOutputs(FoodStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.food.FoodStore;
import com.traclabs.biosim.idl.simulation.framework.FoodConsumerDefinitionOperations;

/**
 * @author Scott Bell
 */

public class FoodConsumerDefinitionImpl extends StoreFlowRateControllableImpl implements FoodConsumerDefinitionOperations {
    public void setFoodInputs(FoodStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
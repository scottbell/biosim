package com.traclabs.biosim.server.simulation.food;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.food.FoodMatter;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;

/**
 * @author Scott Bell
 */

public class FoodProducerDefinition extends StoreFlowRateControllable  {

    public FoodProducerDefinition(BioModule pModule) {
    	super(pModule);
    }

    public void setFoodOutputs(FoodStore[] pStores, float[] pMaxFlowRates,
            float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }

    public float pushFoodToStore(FoodMatter[] foodToPush) {
        float fullMassToDistribute = calculateSizeOfFoodMatter(foodToPush);
        float resourceDistributed = fullMassToDistribute;
        FoodMatter[] copyOfMatter = new FoodMatter[foodToPush.length];
        System.arraycopy(foodToPush, 0, copyOfMatter, 0, foodToPush.length);
        for (int i = 0; (i < getStores().length) && (resourceDistributed > 0); i++) {
        	//TODO need to respect flowrates here
        	/*
            float resourceToDistributeFirst = Math.min(resourceDistributed,
                    getMaxFlowRate(i));
            float resourceToDistributeFinal = Math.min(
                    resourceToDistributeFirst, getDesiredFlowRate(i));
            */
            FoodStore currentFoodStore = (FoodStore)(getStores()[i]);
            getActualFlowRates()[i] = currentFoodStore
                    .addFoodMatterArray(copyOfMatter);
            resourceDistributed -= getActualFlowRate(i);
        }
        float amountPushed = (fullMassToDistribute - resourceDistributed);
        return amountPushed;
    }

    private static float calculateSizeOfFoodMatter(FoodMatter[] arrayOfMatter) {
        float totalSize = 0f;
        for (int i = 0; i < arrayOfMatter.length; i++)
            totalSize += arrayOfMatter[i].mass;
        return totalSize;
    }
}
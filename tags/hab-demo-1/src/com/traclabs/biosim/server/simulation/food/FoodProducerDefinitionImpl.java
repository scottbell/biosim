package com.traclabs.biosim.server.simulation.food;

import com.traclabs.biosim.idl.simulation.food.FoodMatter;
import com.traclabs.biosim.idl.simulation.food.FoodProducerDefinition;
import com.traclabs.biosim.idl.simulation.food.FoodProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.food.FoodProducerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.food.FoodStore;
import com.traclabs.biosim.idl.simulation.food.FoodStoreHelper;
import com.traclabs.biosim.server.framework.BioModuleImpl;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class FoodProducerDefinitionImpl extends StoreFlowRateControllableImpl
        implements FoodProducerDefinitionOperations {
    private FoodProducerDefinition myFoodProducerDefinition;

    public FoodProducerDefinitionImpl(BioModuleImpl pModule) {
    	super(pModule);
    	FoodProducerDefinitionPOATie tie = new FoodProducerDefinitionPOATie(this);
    	myFoodProducerDefinition = tie._this(OrbUtils.getORB());
    }

    public FoodProducerDefinition getCorbaObject() {
        return myFoodProducerDefinition;
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
            FoodStore currentFoodStore = FoodStoreHelper.narrow(getStores()[i]);
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
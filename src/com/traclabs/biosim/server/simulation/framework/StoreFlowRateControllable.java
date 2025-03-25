package com.traclabs.biosim.server.simulation.framework;

import java.util.Arrays;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.Store;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableOperations;
import com.traclabs.biosim.server.framework.BioModule;

/**
 * @author Scott Bell
 */

public abstract class StoreFlowRateControllable extends
        SingleFlowRateControllable implements
        StoreFlowRateControllableOperations {
    private Store[] myStores = new Store[0];
    private Store[] myInitialStores = new Store[0];
    
    public StoreFlowRateControllable(BioModule pModule){
    	super(pModule);
        myStores = new Store[0];
    }
    
    public void reset(){
    	super.reset();
        System.arraycopy(myInitialStores, 0, myStores, 0, myInitialStores.length);
    }

    public Store[] getStores() {
        return myStores;
    }
    
    public void setStore(Store store, int index){
    	myStores[index] = store;
    }

    public void setInitialStores(Store[] pStores) {
    	myInitialStores = pStores;
    	myStores = new Store[myInitialStores.length];
        System.arraycopy(myInitialStores, 0, myStores, 0, myInitialStores.length);
        float[] emptyActualFlowRates = new float[pStores.length];
        if (getDesiredFlowRates().length != pStores.length)
        	myLogger.warn("Desired flow rate cardinality is "+getDesiredFlowRates().length+" while store cardinality is "+pStores.length);
        if (getMaxFlowRates().length != pStores.length)
        	myLogger.warn("Max flow rate cardinality is "+getMaxFlowRates().length+" while store cardinality is "+pStores.length);
        setInitialActualFlowRates(emptyActualFlowRates);
    }
    
    public boolean connectsTo(Store pStore){
    	for (Store store : myStores){
    		if (store._is_equivalent(pStore))
    			return true;
    	}
    	return false;
    }

    /**
     * Grabs as much resources as it can (i.e., the maxFlowRate) from stores.
     * 
     * @return The total amount of resource grabbed from the stores
     */
    public float getMostResourceFromStores() {
        if (getStores() == null)
            return 0f;
        float gatheredResource = 0f;
        for (int i = 0; i < getStores().length; i++) {
            float amountToTake = Math.min(getMaxFlowRate(i),
                    getDesiredFlowRate(i));
            amountToTake = randomFilter(amountToTake);
            getActualFlowRates()[i] = getStores()[i].take(amountToTake);
            gatheredResource += getActualFlowRate(i);
        }
        return gatheredResource;
    }
    
    /**
     * Grabs as much resources as it can (i.e., the maxFlowRate) from a store.
     * 
     * @param indexOfStore
     *            The store to get from
     * @return The total amount of resource grabbed from a stores
     */
    public float getMostResourceFromStore(int indexOfStore) {
        if (getStores() == null)
            return 0f;
        if (getStores().length <= indexOfStore)
        	return 0f;
        float gatheredResource = 0f;
        float amountToTake = Math.min(getMaxFlowRate(indexOfStore), getDesiredFlowRate(indexOfStore));
        amountToTake = randomFilter(amountToTake);
        getActualFlowRates()[indexOfStore] = getStores()[indexOfStore].take(amountToTake);
        gatheredResource += getActualFlowRate(indexOfStore);
        return gatheredResource;
    }

    /**
     * Attempts to grab a specified amount from a collection of stores
     * 
     * @param amountNeeded
     *            The amount to gather from the stores
     * @return The total amount of resource grabbed from the stores (equal to
     *         the amount needed if sucessful)
     */
    public float getResourceFromStores(float amountNeeded) {
        if (getStores() == null)
            return 0f;
        if (amountNeeded <= 0){
        	Arrays.fill(getActualFlowRates(), 0f);
        	return 0f;
        }
        float gatheredResource = 0f;
        for (int i = 0; (i < getStores().length)
                && (gatheredResource < amountNeeded); i++) {
            float resourceToGatherFirst = Math.min(amountNeeded,
                    getMaxFlowRate(i));
            float resourceToGatherFinal = Math.min(resourceToGatherFirst,
                    getDesiredFlowRate(i));
            resourceToGatherFinal = randomFilter(resourceToGatherFinal);
            getActualFlowRates()[i] = getStores()[i]
                    .take(resourceToGatherFinal);
            gatheredResource += getActualFlowRate(i);
        }
        return gatheredResource;
    }

    /**
     * Attempts to grab a specified amount from a collection of stores
     * 
     * @param amountNeeded
     *            The amount to gather from the stores
     * @param fraction
     *            what to multiply each flow rate by
     * @return The total amount of resource grabbed from the stores (equal to
     *         the amount needed if sucessful)
     */
    public float getFractionalResourceFromStores(float amountNeeded,
            float fraction) {
        if (getStores() == null)
            return 0f;
        if (amountNeeded <= 0){
        	Arrays.fill(getActualFlowRates(), 0f);
        	return 0f;
        }
        float gatheredResource = 0f;
        for (int i = 0; (i < getStores().length)
                && (gatheredResource < amountNeeded); i++) {
            float resourceToGatherFirst = Math.min(amountNeeded,
                    getMaxFlowRate(i) * fraction);
            float resourceToGatherFinal = Math.min(resourceToGatherFirst,
                    getDesiredFlowRate(i) * fraction);
            resourceToGatherFinal = randomFilter(resourceToGatherFinal);
            float grabbed = getStores()[i].take(resourceToGatherFinal);
            getActualFlowRates()[i] += grabbed;
            gatheredResource += grabbed;
        }
        return gatheredResource;
    }

    /**
     * Attempts to grab a specified amount from a collection of stores
     * @param myBioModule 
     * 
     * @param pDefinition
     *            The defintion to grab the resources from
     * @param amountNeeded
     *            The amount to gather from the stores
     * @param fraction
     *            what to multiply each flow rate by
     * @return The total amount of resource grabbed from the stores (equal to
     *         the amount needed if sucessful)
     */
    public static float getFractionalResourceFromStores(
            BioModule myBioModule, StoreFlowRateControllable pDefinition, float amountNeeded,
            float fraction) {
        if (pDefinition.getStores() == null)
            return 0f;
        if (amountNeeded <= 0){
        	Arrays.fill(pDefinition.getActualFlowRates(), 0f);
        	return 0f;
        }
        float gatheredResource = 0f;
        for (int i = 0; (i < pDefinition.getStores().length)
                && (gatheredResource < amountNeeded); i++) {
            float resourceToGatherFirst = Math.min(amountNeeded, pDefinition
                    .getMaxFlowRate(i)
                    * fraction);
            float resourceToGatherFinal = Math.min(resourceToGatherFirst,
                    pDefinition.getDesiredFlowRate(i) * fraction);
            resourceToGatherFinal = myBioModule.randomFilter(resourceToGatherFinal);
            float grabbed = pDefinition.getStores()[i]
                    .take(resourceToGatherFinal);
            pDefinition.getActualFlowRates()[i] += grabbed;
            gatheredResource += grabbed;
        }
        return gatheredResource;
    }

    /**
     * Attempts to push a specified amount to a collection of stores
     * 
     * @param pDefinition
     *            The definition to push the resources to
     * @param amountToPush
     *            The amount to push to the stores
     * @param fraction
     *            what to multiply each flow rate by
     * @return The total amount of resource pushed to the stores (equal to the
     *         amount to push if sucessful)
     */
    public static float pushFractionalResourceToStores(
            BioModule myBioModule,
            StoreFlowRateControllable pDefinition, float amountToPush,
            float fraction) {
        if (pDefinition.getStores() == null)
            return 0f;
        if (amountToPush <= 0){
        	Arrays.fill(pDefinition.getActualFlowRates(), 0f);
        	return 0f;
        }
        float resourceRemaining = amountToPush;
        for (int i = 0; (i < pDefinition.getStores().length)
                && (resourceRemaining > 0); i++) {
            float resourceToDistributeFirst = Math.min(resourceRemaining,
                    pDefinition.getMaxFlowRate(i) * fraction);
            float resourceToDistributeFinal = Math.min(
                    resourceToDistributeFirst, pDefinition
                            .getDesiredFlowRate(i)
                            * fraction);
            resourceToDistributeFinal = myBioModule.randomFilter(resourceToDistributeFinal);
            float given = pDefinition.getStores()[i]
                    .add(resourceToDistributeFinal);
            pDefinition.getActualFlowRates()[i] += given;
            resourceRemaining -= given;
        }
        return (amountToPush - resourceRemaining);
    }

    /**
     * Attempts to push a specified amount to a collection of stores
     * 
     * @param amountToPush
     *            The amount to push to the stores
     * @return The total amount of resource pushed to the stores (equal to the
     *         amount to push if sucessful)
     */
    public float pushResourceToStores(float amountToPush) {
        if (getStores() == null)
            return 0f;
        if (amountToPush <= 0){
        	Arrays.fill(getActualFlowRates(), 0f);
        	return 0f;
        }
        float resourceRemaining = amountToPush;
        for (int i = 0; (i < getStores().length) && (resourceRemaining > 0); i++) {
            float resourceToDistributeFirst = Math.min(resourceRemaining,
                    getMaxFlowRate(i));
            float resourceToDistributeFinal = Math.min(
                    resourceToDistributeFirst, getDesiredFlowRate(i));
            getStores()[i].add(resourceToDistributeFinal);
            getActualFlowRates()[i] = resourceToDistributeFinal;
            resourceRemaining -= getActualFlowRate(i);
        }
        return (amountToPush - resourceRemaining);
    }

    /**
     * Attempts to push a specified amount to a collection of stores
     * 
     * @param amountToPush
     *            The amount to push to the stores
     * @param fraction
     *            what to multiply each flow rate by
     * @return The total amount of resource pushed to the stores (equal to the
     *         amount to push if sucessful)
     */
    public float pushFractionalResourceToStores(float amountToPush,
            float fraction) {
        if (getStores() == null)
            return 0f;
        if (amountToPush <= 0){
        	Arrays.fill(getActualFlowRates(), 0f);
        	return 0f;
        }
        float resourceRemaining = amountToPush;
        for (int i = 0; (i < getStores().length) && (resourceRemaining > 0); i++) {
            float resourceToDistributeFirst = Math.min(resourceRemaining,
                    getMaxFlowRate(i) * fraction);
            float resourceToDistributeFinal = Math
                    .min(resourceToDistributeFirst, getDesiredFlowRate(i)
                            * fraction);
            float given = getStores()[i].add(resourceToDistributeFinal);
            getActualFlowRates()[i] += given;
            resourceRemaining -= given;
        }
        return (amountToPush - resourceRemaining);
    }
    
    /**
     * Attempts to push a specified amount to a store
     * 
     * @param amountToPush
     *            The amount to push to the stores
     * @param indexOfStore
     *            The store to push to
     * @return The total amount of resource pushed to the store (equal to the
     *         amount to push if sucessful)
     */
    public float pushResourceToStore(float amountToPush, int indexOfStore) {
        if (getStores() == null)
            return 0f;
        if (getStores().length <= indexOfStore)
        	return 0f;
        if (amountToPush <= 0){
        	Arrays.fill(getActualFlowRates(), 0f);
        	return 0f;
        }
        float resourceRemaining = amountToPush;
        float resourceToDistributeFirst = Math.min(resourceRemaining, getMaxFlowRate(indexOfStore));
        float resourceToDistributeFinal = Math.min(resourceToDistributeFirst, getDesiredFlowRate(indexOfStore));
        getActualFlowRates()[indexOfStore] = getStores()[indexOfStore].add(resourceToDistributeFinal);
        resourceRemaining -= getActualFlowRate(indexOfStore);
        return (amountToPush - resourceRemaining);
    }
}
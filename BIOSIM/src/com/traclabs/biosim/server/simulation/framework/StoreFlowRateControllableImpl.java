package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.framework.Store;
import com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllable;
import com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations;

/**
 * @author Scott Bell
 */

public abstract class StoreFlowRateControllableImpl extends SingleFlowRateControllableImpl implements StoreFlowRateControllableOperations {
    private Store[] myStores;
    
    public Store[] getStores() {
       return myStores;
    }
    
    protected void setStores(Store[] pStores){
        myStores = pStores;
        float[] emptyActualFlowRates = new float[pStores.length];
        setActualFlowRates(emptyActualFlowRates);
    }
    
    /**
     * Grabs as much resources as it can (i.e., the maxFlowRate) from a store.
     * @return The total amount of resource grabbed from the stores
     */
    public float getMostResourceFromStore() {
        if (getStores() == null)
            return 0f;
        float gatheredResource = 0f;
        for (int i = 0; i < getStores().length; i++) {
            float amountToTake = Math.min(getMaxFlowRate(i),
                    getDesiredFlowRate(i));
            getActualFlowRates()[i] = getStores()[i].take(amountToTake);
            gatheredResource += getActualFlowRate(i);
        }
        return gatheredResource;
    }
    
    /**
     * Attempts to grab a specified amount from a collection of stores
     * @param amountNeeded
     *            The amount to gather from the stores
     * @return The total amount of resource grabbed from the stores (equal to
     *         the amount needed if sucessful)
     */
    public float getResourceFromStore(float amountNeeded) {
        if (getStores() == null)
            return 0f;
        float gatheredResource = 0f;
        for (int i = 0; (i < getStores().length)
                && (gatheredResource < amountNeeded); i++) {
            float resourceToGatherFirst = Math.min(amountNeeded,
                    getMaxFlowRate(i));
            float resourceToGatherFinal = Math.min(resourceToGatherFirst,
                    getDesiredFlowRate(i));
            getActualFlowRates()[i] = getStores()[i].take(resourceToGatherFinal);
            gatheredResource += getActualFlowRate(i);
        }
        return gatheredResource;
    }

    /**
     * Attempts to grab a specified amount from a collection of stores
     * @param amountNeeded
     *            The amount to gather from the stores
     * @param fraction
     *            what to multiply each flow rate by
     * @return The total amount of resource grabbed from the stores (equal to
     *         the amount needed if sucessful)
     */
    public float getFractionalResourceFromStore(float amountNeeded, float fraction) {
        if (getStores() == null)
            return 0f;
        float gatheredResource = 0f;
        for (int i = 0; (i < getStores().length)
                && (gatheredResource < amountNeeded); i++) {
            float resourceToGatherFirst = Math.min(amountNeeded,
                    getMaxFlowRate(i) * fraction);
            float resourceToGatherFinal = Math.min(resourceToGatherFirst,
                    getDesiredFlowRate(i) * fraction);
            float grabbed = getStores()[i].take(resourceToGatherFinal);
            getActualFlowRates()[i] += grabbed;
            gatheredResource += grabbed;
        }
        return gatheredResource;
    }
    
    /**
     * Attempts to grab a specified amount from a collection of stores
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
    public static float getFractionalResourceFromStore(StoreFlowRateControllable pDefinition, float amountNeeded, float fraction) {
        if (pDefinition.getStores() == null)
            return 0f;
        float gatheredResource = 0f;
        for (int i = 0; (i < pDefinition.getStores().length)
                && (gatheredResource < amountNeeded); i++) {
            float resourceToGatherFirst = Math.min(amountNeeded,
                    pDefinition.getMaxFlowRate(i) * fraction);
            float resourceToGatherFinal = Math.min(resourceToGatherFirst,
                    pDefinition.getDesiredFlowRate(i) * fraction);
            float grabbed = pDefinition.getStores()[i].take(resourceToGatherFinal);
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
    public static float pushFractionalResourceToStore(StoreFlowRateControllable pDefinition, float amountToPush, float fraction) {
        if (pDefinition.getStores() == null)
            return 0f;
        float resourceRemaining = amountToPush;
        for (int i = 0; (i < pDefinition.getStores().length) && (resourceRemaining > 0); i++) {
            float resourceToDistributeFirst = Math.min(resourceRemaining,
                    pDefinition.getMaxFlowRate(i) * fraction);
            float resourceToDistributeFinal = Math.min(
                    resourceToDistributeFirst, pDefinition.getDesiredFlowRate(i) * fraction);
            float given = pDefinition.getStores()[i].add(resourceToDistributeFinal);
            pDefinition.getActualFlowRates()[i] += given;
            resourceRemaining -= given;
        }
        return (amountToPush - resourceRemaining);
    }
    
    
    /**
     * Attempts to push a specified amount to a collection of stores
     * @param amountToPush
     *            The amount to push to the stores
     * @return The total amount of resource pushed to the stores (equal to the
     *         amount to push if sucessful)
     */
    public float pushResourceToStore(float amountToPush) {
        if (getStores() == null)
            return 0f;
        float resourceRemaining = amountToPush;
        for (int i = 0; (i < getStores().length) && (resourceRemaining > 0); i++) {
            float resourceToDistributeFirst = Math.min(resourceRemaining,
                    getMaxFlowRate(i));
            float resourceToDistributeFinal = Math.min(
                    resourceToDistributeFirst, getDesiredFlowRate(i));
            getActualFlowRates()[i] = getStores()[i].add(resourceToDistributeFinal);
            resourceRemaining -= getActualFlowRate(i);
        }
        return (amountToPush - resourceRemaining);
    }

    /**
     * Attempts to push a specified amount to a collection of stores
     * @param amountToPush
     *            The amount to push to the stores
     * @param fraction
     *            what to multiply each flow rate by
     * @return The total amount of resource pushed to the stores (equal to the
     *         amount to push if sucessful)
     */
    public float pushFractionalResourceToStore(float amountToPush, float fraction) {
        if (getStores() == null)
            return 0f;
        float resourceRemaining = amountToPush;
        for (int i = 0; (i < getStores().length) && (resourceRemaining > 0); i++) {
            float resourceToDistributeFirst = Math.min(resourceRemaining,
                    getMaxFlowRate(i) * fraction);
            float resourceToDistributeFinal = Math.min(
                    resourceToDistributeFirst, getDesiredFlowRate(i) * fraction);
            float given = getStores()[i].add(resourceToDistributeFinal);
            getActualFlowRates()[i] += given;
            resourceRemaining -= given;
        }
        return (amountToPush - resourceRemaining);
    }
}
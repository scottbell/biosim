package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.framework.SimBioModuleOperations;
import com.traclabs.biosim.idl.simulation.framework.Store;
import com.traclabs.biosim.server.framework.BioModuleImpl;

/**
 * The SimBioModule Implementation. Modules specific to the simulation.
 * 
 * @author Scott Bell
 */

public abstract class SimBioModuleImpl extends BioModuleImpl implements
        SimBioModuleOperations {

    /**
     * Constructor to create a BioModule, should only be called by those
     * deriving from BioModule.
     * 
     * @param pID
     *            The unique ID for this module (all the modules this module
     *            communicates with should have the same ID)
     */
    protected SimBioModuleImpl(int pID, String pName) {
        super(pID, pName);
    }

    /**
     * Grabs as much resources as it can (i.e., the maxFlowRate) from a store.
     * 
     * @param pStores
     *            The stores to grab the resources from
     * @param pMaxFlowRates
     *            The max flow rates from this module to the stores
     * @param pActualFlowRates
     *            The actual flow rates from this module to the stores
     * @return The total amount of resource grabbed from the stores
     */
    public static float getMostResourceFromStore(Store[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates,
            float[] pActualFlowRates) {
        float gatheredResource = 0f;
        for (int i = 0; i < pStores.length; i++) {
            float amountToTake = Math.min(pMaxFlowRates[i],
                    pDesiredFlowRates[i]);
            pActualFlowRates[i] = pStores[i].take(amountToTake);
            gatheredResource += pActualFlowRates[i];
        }
        return gatheredResource;
    }

    /**
     * Attempts to grab a specified amount from a collection of stores
     * 
     * @param pStores
     *            The stores to grab the resources from
     * @param pMaxFlowRates
     *            The flow rates from this module to the stores
     * @param pDesiredFlowRates
     *            The desired flow rates from this module to the stores
     * @param pActualFlowRates
     *            The actual flow rates from this module to the stores
     * @param amountNeeded
     *            The amount to gather from the stores
     * @return The total amount of resource grabbed from the stores (equal to
     *         the amount needed if sucessful)
     */
    public static float getResourceFromStore(Store[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates,
            float[] pActualFlowRates, float amountNeeded) {
        float gatheredResource = 0f;
        for (int i = 0; (i < pStores.length)
                && (gatheredResource < amountNeeded); i++) {
            float resourceToGatherFirst = Math.min(amountNeeded,
                    pMaxFlowRates[i]);
            float resourceToGatherFinal = Math.min(resourceToGatherFirst,
                    pDesiredFlowRates[i]);
            pActualFlowRates[i] = pStores[i].take(resourceToGatherFinal);
            gatheredResource += pActualFlowRates[i];
        }
        return gatheredResource;
    }

    /**
     * Attempts to grab a specified amount from a collection of stores
     * 
     * @param pStores
     *            The stores to grab the resources from
     * @param pMaxFlowRates
     *            The flow rates from this module to the stores
     * @param pDesiredFlowRates
     *            The desired flow rates from this module to the stores
     * @param pActualFlowRates
     *            The actual flow rates from this module to the stores
     *            (incremented here)
     * @param amountNeeded
     *            The amount to gather from the stores
     * @param fraction
     *            what to multiply each flow rate by
     * @return The total amount of resource grabbed from the stores (equal to
     *         the amount needed if sucessful)
     */
    public float getFractionalResourceFromStore(Store[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates,
            float[] pActualFlowRates, float amountNeeded, float fraction) {
        float gatheredResource = 0f;
        for (int i = 0; (i < pStores.length)
                && (gatheredResource < amountNeeded); i++) {
            float resourceToGatherFirst = Math.min(amountNeeded,
                    pMaxFlowRates[i] * fraction);
            float resourceToGatherFinal = Math.min(resourceToGatherFirst,
                    pDesiredFlowRates[i] * fraction);
            float grabbed = pStores[i].take(resourceToGatherFinal);
            pActualFlowRates[i] += grabbed;
            gatheredResource += grabbed;
        }
        return gatheredResource;
    }

    /**
     * Attempts to push a specified amount to a collection of stores
     * 
     * @param pStores
     *            The stores to push the resources to
     * @param pMaxFlowRates
     *            The flow rates from this module to the stores
     * @param pDesiredFlowRates
     *            The desired flow rates from this module to the stores
     * @param pActualFlowRates
     *            The actual flow rates from this module to the stores
     * @param amountToPush
     *            The amount to push to the stores
     * @return The total amount of resource pushed to the stores (equal to the
     *         amount to push if sucessful)
     */
    public static float pushResourceToStore(Store[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates,
            float[] pActualFlowRates, float amountToPush) {
        float resourceRemaining = amountToPush;
        for (int i = 0; (i < pStores.length) && (resourceRemaining > 0); i++) {
            float resourceToDistributeFirst = Math.min(resourceRemaining,
                    pMaxFlowRates[i]);
            float resourceToDistributeFinal = Math.min(
                    resourceToDistributeFirst, pDesiredFlowRates[i]);
            pActualFlowRates[i] = pStores[i].add(resourceToDistributeFinal);
            resourceRemaining -= pActualFlowRates[i];
        }
        return (amountToPush - resourceRemaining);
    }

    /**
     * Attempts to push a specified amount to a collection of stores
     * 
     * @param pStores
     *            The stores to push the resources to
     * @param pMaxFlowRates
     *            The max flow rates from this module to the stores
     * @param pDesiredFlowRates
     *            The desired flow rates from this module to the stores
     * @param pActualFlowRates
     *            The actual flow rates from this module to the stores
     *            (incremented here)
     * @param amountToPush
     *            The amount to push to the stores
     * @param fraction
     *            what to multiply each flow rate by
     * @return The total amount of resource pushed to the stores (equal to the
     *         amount to push if sucessful)
     */
    public static float pushFractionalResourceToStore(Store[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates,
            float[] pActualFlowRates, float amountToPush, float fraction) {
        float resourceRemaining = amountToPush;
        for (int i = 0; (i < pStores.length) && (resourceRemaining > 0); i++) {
            float resourceToDistributeFirst = Math.min(resourceRemaining,
                    pMaxFlowRates[i] * fraction);
            float resourceToDistributeFinal = Math.min(
                    resourceToDistributeFirst, pDesiredFlowRates[i] * fraction);
            float given = pStores[i].add(resourceToDistributeFinal);
            pActualFlowRates[i] += given;
            resourceRemaining -= given;
        }
        return (amountToPush - resourceRemaining);
    }
}


package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.air.Breath;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.Store;
import com.traclabs.biosim.idl.simulation.framework.StoreEnvironmentFlowRateControllableOperations;
import com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllable;

/**
 * @author Scott Bell
 */

public abstract class StoreEnvironmentFlowRateControllableImpl implements StoreEnvironmentFlowRateControllableOperations {
    private SimEnvironment[] mySimEnvironment;
    private Store[] myStores;
    
    private float[] myStoreMaxFlowRates;

    private float[] myStoreActualFlowRates;

    private float[] myStoreDesiredFlowRates;
    
    private float[] myEnvironmentMaxFlowRates;

    private float[] myEnvironmentActualFlowRates;

    private float[] myEnvironmentDesiredFlowRates;
    
    public StoreEnvironmentFlowRateControllableImpl(){
        myStoreMaxFlowRates = new float[0];
        myStoreMaxFlowRates = new float[0];
        myStoreDesiredFlowRates = new float[0];
        myEnvironmentMaxFlowRates = new float[0];
        myEnvironmentActualFlowRates = new float[0];
        myEnvironmentDesiredFlowRates = new float[0];
    }
    
    //Stores
    public Store[] getStores() {
       return myStores;
    }
    
    protected void setStores(Store[] pStores){
        myStores = pStores;
        float[] emptyActualFlowRates = new float[pStores.length];
        setStoreActualFlowRates(emptyActualFlowRates);
    }
    
    public void setStoreMaxFlowRate(float value, int index) {
        myStoreMaxFlowRates[index] = value;
    }
    
    public float getStoreMaxFlowRate(int index) {
        return myStoreMaxFlowRates[index];
    }

    public void setStoreDesiredFlowRate(float value, int index) {
        myStoreDesiredFlowRates[index] = value;
    }

    public float getStoreDesiredFlowRate(int index) {
        return myStoreDesiredFlowRates[index];
    }

    public float getStoreActualFlowRate(int index) {
        return myStoreActualFlowRates[index];
    }
    
    public float[] getStoreMaxFlowRates() {
        return myStoreMaxFlowRates;
    }
    
    public float[] getStoreDesiredFlowRates() {
        return myStoreDesiredFlowRates;
    }

    public float[] getStoreActualFlowRates() {
        return myStoreActualFlowRates;
    }
    
    protected void setStoreMaxFlowRates(float[] pStoreMaxFlowRates) {
        myStoreMaxFlowRates = pStoreMaxFlowRates;
    }
    
    protected void setStoreDesiredFlowRates(float[] pStoreDesiredFlowRates) {
        myStoreDesiredFlowRates = pStoreDesiredFlowRates;
    }

    protected void setStoreActualFlowRates(float[] pStoreActualFlowRates) {
        myStoreActualFlowRates = pStoreActualFlowRates;
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
            float amountToTake = Math.min(getStoreMaxFlowRate(i),
                    getStoreDesiredFlowRate(i));
            getStoreActualFlowRates()[i] = getStores()[i].take(amountToTake);
            gatheredResource += getStoreActualFlowRate(i);
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
                    getStoreMaxFlowRate(i));
            float resourceToGatherFinal = Math.min(resourceToGatherFirst,
                    getStoreDesiredFlowRate(i));
            getStoreActualFlowRates()[i] = getStores()[i].take(resourceToGatherFinal);
            gatheredResource += getStoreActualFlowRate(i);
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
                    getStoreMaxFlowRate(i) * fraction);
            float resourceToGatherFinal = Math.min(resourceToGatherFirst,
                    getStoreDesiredFlowRate(i) * fraction);
            float grabbed = getStores()[i].take(resourceToGatherFinal);
            getStoreActualFlowRates()[i] += grabbed;
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
                    getStoreMaxFlowRate(i));
            float resourceToDistributeFinal = Math.min(
                    resourceToDistributeFirst, getStoreDesiredFlowRate(i));
            getStoreActualFlowRates()[i] = getStores()[i].add(resourceToDistributeFinal);
            resourceRemaining -= getStoreActualFlowRate(i);
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
                    getStoreMaxFlowRate(i) * fraction);
            float resourceToDistributeFinal = Math.min(
                    resourceToDistributeFirst, getStoreDesiredFlowRate(i) * fraction);
            float given = getStores()[i].add(resourceToDistributeFinal);
            getStoreActualFlowRates()[i] += given;
            resourceRemaining -= given;
        }
        return (amountToPush - resourceRemaining);
    }
    
    //Environments
    public SimEnvironment[] getEnvironments() {
       return mySimEnvironment;
    }
    
    protected void setEnvironments(SimEnvironment[] pSimEnvironments){
        mySimEnvironment = pSimEnvironments;
        float[] emptyActualFlowRates = new float[pSimEnvironments.length];
        setEnvironmentActualFlowRates(emptyActualFlowRates);
    }
    
    public void setEnvironmentMaxFlowRate(float value, int index) {
        myEnvironmentMaxFlowRates[index] = value;
    }
    
    public float getEnvironmentMaxFlowRate(int index) {
        return myEnvironmentMaxFlowRates[index];
    }

    public void setEnvironmentDesiredFlowRate(float value, int index) {
        myEnvironmentDesiredFlowRates[index] = value;
    }

    public float getEnvironmentDesiredFlowRate(int index) {
        return myEnvironmentDesiredFlowRates[index];
    }

    public float getEnvironmentActualFlowRate(int index) {
        return myEnvironmentActualFlowRates[index];
    }
    
    public float[] getEnvironmentMaxFlowRates() {
        return myEnvironmentMaxFlowRates;
    }
    
    public float[] getEnvironmentDesiredFlowRates() {
        return myEnvironmentDesiredFlowRates;
    }

    public float[] getEnvironmentActualFlowRates() {
        return myEnvironmentActualFlowRates;
    }
    
    protected void setEnvironmentMaxFlowRates(float[] pEnvironmentMaxFlowRates) {
        myEnvironmentMaxFlowRates = pEnvironmentMaxFlowRates;
    }
    
    protected void setEnvironmentDesiredFlowRates(float[] pEnvironmentDesiredFlowRates) {
        myEnvironmentDesiredFlowRates = pEnvironmentDesiredFlowRates;
    }

    protected void setEnvironmentActualFlowRates(float[] pEnvironmentActualFlowRates) {
        myEnvironmentActualFlowRates = pEnvironmentActualFlowRates;
    }
    
    /**
     * Attempts to grab a specified number of air moles from a collection of environments
     * @param molesNeeded
     *            The amount to gather from the stores
     * @return Breath of air consumed
     */
    public Breath getAirFromEnvironment(float molesNeeded) {
        if (getEnvironments() == null)
            return new Breath(0f, 0f, 0f, 0f, 0f);
        float gatheredAir = 0f;
        float gatheredO2 = 0f;
        float gatheredCO2 = 0f;
        float gatheredOther = 0f;
        float gatheredWater = 0f;
        float gatheredNitrogen = 0f;
        for (int i = 0; (i < getEnvironments().length)
                && (gatheredAir < molesNeeded); i++) {
            float resourceToGatherFirst = Math.min(molesNeeded, getEnvironmentMaxFlowRate(i));
            float resourceToGatherFinal = Math.min(resourceToGatherFirst,
                    getEnvironmentDesiredFlowRate(i));
            Breath currentBreath = getEnvironments()[i]
                    .takeAirMoles(resourceToGatherFinal);
            gatheredAir += currentBreath.O2 + currentBreath.CO2
                    + currentBreath.other + currentBreath.water
                    + currentBreath.nitrogen;
            getEnvironmentActualFlowRates()[i] = gatheredAir;
            gatheredO2 += currentBreath.O2;
            gatheredCO2 += currentBreath.CO2;
            gatheredOther += currentBreath.other;
            gatheredWater += currentBreath.water;
            gatheredNitrogen += currentBreath.nitrogen;
        }
        Breath returnBreath = new Breath();
        returnBreath.O2 = gatheredO2;
        returnBreath.CO2 = gatheredCO2;
        returnBreath.other = gatheredOther;
        returnBreath.water = gatheredWater;
        returnBreath.nitrogen = gatheredNitrogen;
        return returnBreath;
    }
    
    /**
     * Attempts to grab a most number of air moles from a collection of environments
     * @return Breath of air consumed
     */
    public Breath getMostAirFromEnvironment() {
        if (getEnvironments() == null)
            return new Breath(0f, 0f, 0f, 0f, 0f);
        float gatheredAir = 0f;
        float gatheredO2 = 0f;
        float gatheredCO2 = 0f;
        float gatheredOther = 0f;
        float gatheredWater = 0f;
        float gatheredNitrogen = 0f;
        for (int i = 0; (i < getEnvironments().length); i++) {
            float resourceToGatherFinal = Math.min(getEnvironmentMaxFlowRate(i),
                    getEnvironmentDesiredFlowRate(i));
            Breath currentBreath = getEnvironments()[i]
                    .takeAirMoles(resourceToGatherFinal);
            gatheredAir += currentBreath.O2 + currentBreath.CO2
                    + currentBreath.other + currentBreath.water
                    + currentBreath.nitrogen;
            getEnvironmentActualFlowRates()[i] = gatheredAir;
            gatheredO2 += currentBreath.O2;
            gatheredCO2 += currentBreath.CO2;
            gatheredOther += currentBreath.other;
            gatheredWater += currentBreath.water;
            gatheredNitrogen += currentBreath.nitrogen;
        }
        Breath returnBreath = new Breath();
        returnBreath.O2 = gatheredO2;
        returnBreath.CO2 = gatheredCO2;
        returnBreath.other = gatheredOther;
        returnBreath.water = gatheredWater;
        returnBreath.nitrogen = gatheredNitrogen;
        return returnBreath;
    }
    
    /**
     * Attempts to push specified air moles to a collection of environments
     * @param amountToPush
     *            The amount of moles to push to the environments
     * @return The total amount of air pushed to the environments (equal to the
     *         amount to push if sucessful)
     */
    public Breath pushAirToEnvironments(Breath breathToPush) {
        if (getEnvironments() == null)
            return new Breath(0f, 0f, 0f, 0f, 0f);
        float distributedO2Left = breathToPush.O2;
        float distributedCO2Left = breathToPush.CO2;
        float distributedOtherLeft = breathToPush.other;
        float distributedWaterLeft = breathToPush.water;
        float distributedNitrogenLeft = breathToPush.nitrogen;
        for (int i = 0; (i < getEnvironments().length)
                && ((distributedO2Left > 0) || (distributedCO2Left > 0) || (distributedOtherLeft > 0)
                        || (distributedWaterLeft > 0) || (distributedNitrogenLeft > 0)); i++) {
            float totalToDistribute = distributedO2Left + distributedCO2Left + distributedOtherLeft
                    + distributedWaterLeft + distributedNitrogenLeft;
            float resourceToDistributeFirst = Math.min(totalToDistribute,
                    getEnvironmentMaxFlowRate(i));
            float resourceToDistributeFinal = Math.min(
                    resourceToDistributeFirst, getEnvironmentDesiredFlowRate(i));
            //Recalculate percentages based on smaller volume
            float reducedO2ToPass = resourceToDistributeFinal
                    * (distributedO2Left / totalToDistribute);
            float reducedCO2ToPass = resourceToDistributeFinal
            * (distributedCO2Left / totalToDistribute);
            float reducedOtherToPass = resourceToDistributeFinal
                    * (distributedOtherLeft / totalToDistribute);
            float reducedWaterToPass = resourceToDistributeFinal
                    * (distributedWaterLeft / totalToDistribute);
            float reducedNitrogenToPass = resourceToDistributeFinal
                    * (distributedNitrogenLeft / totalToDistribute);
            float O2Added = getEnvironments()[i]
                    .addO2Moles(reducedO2ToPass);
            float CO2Added = getEnvironments()[i].addCO2Moles(reducedCO2ToPass);
            float otherAdded = getEnvironments()[i]
                    .addOtherMoles(reducedOtherToPass);
            float waterAdded = getEnvironments()[i]
                    .addWaterMoles(reducedWaterToPass);
            float nitrogenAdded = getEnvironments()[i]
                    .addNitrogenMoles(reducedNitrogenToPass);
            distributedO2Left -= O2Added;
            distributedCO2Left -= CO2Added;
            distributedOtherLeft -= otherAdded;
            distributedWaterLeft -= waterAdded;
            distributedNitrogenLeft -= nitrogenAdded;
            getEnvironmentActualFlowRates()[i] = reducedO2ToPass + reducedCO2ToPass
                    + reducedOtherToPass + reducedWaterToPass
                    + reducedNitrogenToPass;
        }
        Breath airRemaining = new Breath();
        airRemaining.O2 = breathToPush.O2 - distributedO2Left;
        airRemaining.CO2 = breathToPush.CO2 - distributedCO2Left;
        airRemaining.other = breathToPush.other - distributedOtherLeft;
        airRemaining.water = breathToPush.water - distributedWaterLeft;
        airRemaining.nitrogen = breathToPush.nitrogen - distributedNitrogenLeft;
        return airRemaining;
    }
}
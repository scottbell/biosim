package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.air.Breath;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.EnvironmentFlowRateControllableOperations;

/**
 * @author Scott Bell
 */

public abstract class EnvironmentFlowRateControllableImpl extends SingleFlowRateControllableImpl implements EnvironmentFlowRateControllableOperations {
    private SimEnvironment[] mySimEnvironments;
    
    public SimEnvironment[] getEnvironments() {
       return mySimEnvironments;
    }
    
    protected void setEnvironments(SimEnvironment[] pSimEnvironments){
        mySimEnvironments = pSimEnvironments;
        float[] emptyActualFlowRates = new float[pSimEnvironments.length];
        setActualFlowRates(emptyActualFlowRates);
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
            float resourceToGatherFirst = Math.min(molesNeeded, getMaxFlowRate(i));
            float resourceToGatherFinal = Math.min(resourceToGatherFirst,
                    getDesiredFlowRate(i));
            Breath currentBreath = getEnvironments()[i]
                    .takeAirMoles(resourceToGatherFinal);
            gatheredAir += currentBreath.O2 + currentBreath.CO2
                    + currentBreath.other + currentBreath.water
                    + currentBreath.nitrogen;
            getActualFlowRates()[i] = gatheredAir;
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
            float resourceToGatherFinal = Math.min(getMaxFlowRate(i),
                    getDesiredFlowRate(i));
            Breath currentBreath = getEnvironments()[i]
                    .takeAirMoles(resourceToGatherFinal);
            gatheredAir += currentBreath.O2 + currentBreath.CO2
                    + currentBreath.other + currentBreath.water
                    + currentBreath.nitrogen;
            getActualFlowRates()[i] = gatheredAir;
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
                    getMaxFlowRate(i));
            float resourceToDistributeFinal = Math.min(
                    resourceToDistributeFirst, getDesiredFlowRate(i));
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
            getActualFlowRates()[i] = reducedO2ToPass + reducedCO2ToPass
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
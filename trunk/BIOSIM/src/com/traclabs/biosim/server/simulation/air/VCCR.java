package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.idl.simulation.air.Breath;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;

/**
 * VCCR Subsystem
 * 
 * @author Scott Bell
 */

public class VCCR extends AirRSSubSystem {
    private Breath myBreath;

    private final static float molesAirNeeded = 50.0f;

    private float currentCO2Produced = 0f;

    private float myProductionRate = 1f;

    public VCCR(AirRSImpl pAirRSImpl) {
        super(pAirRSImpl);
        myBreath = new Breath(0f, 0f, 0f, 0f, 0f);
    }

    public void log() {
        super.log();
        myLogger.debug("myBreath=" + myBreath);
        myLogger.debug("molesAirNeeded=" + molesAirNeeded);
        myLogger.debug("currentCO2Produced=" + currentCO2Produced);
        myLogger.debug("myProductionRate=" + myProductionRate);
    }

    public float getAirProduced() {
        return (myBreath.CO2 + myBreath.O2 + myBreath.other);
    }

    public float getCO2Consumed() {
        return myBreath.CO2;
    }

    public float getCO2Produced() {
        return currentCO2Produced;
    }

    public void setProductionRate(float percentage) {
        myProductionRate = percentage;
    }

    public float getProductionRate() {
        return myProductionRate;
    }

    private void gatherAir() {
        float airNeededFiltered = myAirRS.randomFilter(molesAirNeeded
                * myProductionRate);
        float gatheredAir = 0f;
        float gatheredO2 = 0f;
        float gatheredCO2 = 0f;
        float gatheredOther = 0f;
        float gatheredWater = 0f;
        float gatheredNitrogen = 0f;
        for (int i = 0; (i < myAirRS.getAirInputs().length)
                && (gatheredAir < airNeededFiltered); i++) {
            float resourceToGatherFirst = Math.min(airNeededFiltered, myAirRS
                    .getAirInputMaxFlowRate(i));
            float resourceToGatherFinal = Math.min(resourceToGatherFirst,
                    myAirRS.getAirInputDesiredFlowRate(i));
            Breath currentBreath = myAirRS.getAirInputs()[i]
                    .takeAirMoles(resourceToGatherFinal);
            gatheredAir += currentBreath.O2 + currentBreath.CO2
                    + currentBreath.other + currentBreath.water
                    + currentBreath.nitrogen;
            myAirRS.setAirInputActualFlowRate(gatheredAir, i);
            gatheredO2 += currentBreath.O2;
            gatheredCO2 += currentBreath.CO2;
            gatheredOther += currentBreath.other;
            gatheredWater += currentBreath.water;
            gatheredNitrogen += currentBreath.nitrogen;
        }
        myBreath.O2 = gatheredO2;
        myBreath.CO2 = gatheredCO2;
        myBreath.other = gatheredOther;
        myBreath.water = gatheredWater;
        myBreath.nitrogen = gatheredNitrogen;
    }

    private void pushAir() {
        float distributedO2Left = myBreath.O2;
        float distributedOtherLeft = myBreath.other;
        float distributedWaterLeft = myBreath.water;
        float distributedNitrogenLeft = myBreath.nitrogen;
        for (int i = 0; (i < myAirRS.getAirOutputs().length)
                && ((distributedO2Left > 0) || (distributedOtherLeft > 0)
                        || (distributedWaterLeft > 0) || (distributedNitrogenLeft > 0)); i++) {
            float totalToDistribute = distributedO2Left + distributedOtherLeft
                    + distributedWaterLeft + distributedNitrogenLeft;
            float resourceToDistributeFirst = Math.min(totalToDistribute,
                    myAirRS.getAirOutputMaxFlowRate(i));
            float resourceToDistributeFinal = Math.min(
                    resourceToDistributeFirst, myAirRS
                            .getAirOutputDesiredFlowRate(i));
            //Recalculate percentages based on smaller volume
            float reducedO2ToPass = resourceToDistributeFinal
                    * (distributedO2Left / totalToDistribute);
            float reducedOtherToPass = resourceToDistributeFinal
                    * (distributedOtherLeft / totalToDistribute);
            float reducedWaterToPass = resourceToDistributeFinal
                    * (distributedWaterLeft / totalToDistribute);
            float reducedNitrogenToPass = resourceToDistributeFinal
                    * (distributedNitrogenLeft / totalToDistribute);
            float O2Added = myAirRS.getAirOutputs()[i]
                    .addO2Moles(reducedO2ToPass);
            float otherAdded = myAirRS.getAirOutputs()[i]
                    .addOtherMoles(reducedOtherToPass);
            float waterAdded = myAirRS.getAirOutputs()[i]
                    .addWaterMoles(reducedWaterToPass);
            float nitrogenAdded = myAirRS.getAirOutputs()[i]
                    .addNitrogenMoles(reducedNitrogenToPass);
            distributedO2Left -= O2Added;
            distributedOtherLeft -= otherAdded;
            distributedWaterLeft -= waterAdded;
            distributedNitrogenLeft -= nitrogenAdded;
            myAirRS.setAirOutputActualFlowRate(reducedO2ToPass
                    + reducedOtherToPass + reducedWaterToPass
                    + reducedNitrogenToPass, i);
        }
        currentCO2Produced = myBreath.CO2 * myProductionRate;
        float distributedCO2Left = SimBioModuleImpl.pushResourceToStore(myAirRS
                .getCO2Outputs(), myAirRS.getCO2OutputMaxFlowRates(), myAirRS
                .getCO2OutputDesiredFlowRates(), myAirRS
                .getCO2OutputActualFlowRates(), currentCO2Produced);
    }

    public void reset() {
        myBreath = new Breath(0f, 0f, 0f, 0f, 0f);
        currentPowerConsumed = 0;
        currentCO2Produced = 0f;
        hasEnoughPower = false;
    }

    public void tick() {
        super.tick();
        if (hasEnoughPower && enabled) {
            gatherAir();
            pushAir();
        }
    }

}
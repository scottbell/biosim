package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.idl.simulation.air.Breath;

/**
 * VCCR Subsystem
 * 
 * @author Scott Bell
 */

public class VCCRSubsystem extends AirRSSubSystem {
    private Breath myBreath;

    private final static float molesAirNeeded = 50.0f;

    private float currentCO2Produced = 0f;

    private float myProductionRate = 1f;

    public VCCRSubsystem(AirRSImpl pAirRSImpl) {
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
        myBreath = myAirRS.getAirConsumerDefinitionImpl()
                .getAirFromEnvironment(airNeededFiltered);
    }

    private void pushAir() {
        Breath breathToDistribute = new Breath(myBreath.O2, 0f, myBreath.water,
                myBreath.other, myBreath.nitrogen);
        Breath breathDistributed = myAirRS.getAirProducerDefinitionImpl()
                .pushAirToEnvironments(breathToDistribute);
        currentCO2Produced = myBreath.CO2;
        float distributedCO2Left = myAirRS.getCO2ProducerDefinitionImpl()
                .pushResourceToStore(currentCO2Produced);
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
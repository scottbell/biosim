package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.framework.Malfunction;
import com.traclabs.biosim.server.simulation.framework.SimBioModule;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.server.simulation.water.PotableWaterConsumerDefinition;

public class OGS extends SimBioModule {

    //Consumers, Producers
    private final PowerConsumerDefinition myPowerConsumerDefinition;

    private final PotableWaterConsumerDefinition myPotableWaterConsumerDefinition;

    private final O2ProducerDefinition myO2ProducerDefinition;

    private final H2ProducerDefinition myH2ProducerDefinition;
    private final float myProductionRate = 1f;
    private float currentH2OConsumed = 0;
    private float currentO2Produced = 0;
    private float currentH2Produced = 0;
    private float currentPowerConsumed = 0f;

    public OGS(int pID, String pName) {
        super(pID, pName);
        myPowerConsumerDefinition = new PowerConsumerDefinition(this);
        myPotableWaterConsumerDefinition = new PotableWaterConsumerDefinition(this);
        myO2ProducerDefinition = new O2ProducerDefinition(this);
        myH2ProducerDefinition = new H2ProducerDefinition(this);
    }

    @Override
    protected void performMalfunctions() {
        for (Malfunction malfunction : myMalfunctions.values()) {
            malfunction.setPerformed(true);
        }
        if (myMalfunctions.size() > 0) {
            myPowerConsumerDefinition.malfunction();
            myPotableWaterConsumerDefinition.malfunction();
            myO2ProducerDefinition.malfunction();
            myH2ProducerDefinition.malfunction();
        }
    }

    public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinition;
    }

    public PotableWaterConsumerDefinition getPotableWaterConsumerDefinition() {
        return myPotableWaterConsumerDefinition;
    }

    public O2ProducerDefinition getO2ProducerDefinition() {
        return myO2ProducerDefinition;
    }

    public H2ProducerDefinition getH2ProducerDefinition() {
        return myH2ProducerDefinition;
    }

    /**
     * Resets production/consumption levels.
     */
    public void reset() {
        super.reset();
        currentH2OConsumed = 0;
        currentO2Produced = 0;
        currentH2Produced = 0;
        currentPowerConsumed = 0;
        myPowerConsumerDefinition.reset();
        myPotableWaterConsumerDefinition.reset();
        myO2ProducerDefinition.reset();
        myH2ProducerDefinition.reset();
    }

    private void gatherPower() {
        currentPowerConsumed = myPowerConsumerDefinition
                .getMostResourceFromStores();
    }

    public void tick() {
        super.tick();
        gatherPower();
        gatherWater();
        pushGasses();
    }

    public void log() {
        myLogger.debug("power_consumed=" + currentPowerConsumed);
    }

    private void gatherWater() {
        float waterToConsume = (currentPowerConsumed / 75f) * 0.04167f * getTickLength();
        currentH2OConsumed = myPotableWaterConsumerDefinition
                .getResourceFromStores(waterToConsume);
    }

    private void pushGasses() {
        //2H20 --> 2H2 + O2
        float molesOfWater = (currentH2OConsumed * 1000f) / 18.01524f; //1000g/liter,
        // 18.01524g/mole
        float molesOfReactant = molesOfWater / 2f;
        currentO2Produced = molesOfReactant * myProductionRate;
        currentH2Produced = molesOfReactant * 2f
                * myProductionRate;
        float O2ToDistrubute = currentO2Produced;
        float H2ToDistrubute = currentH2Produced;
        myO2ProducerDefinition
                .pushResourceToStores(O2ToDistrubute);
        myH2ProducerDefinition
                .pushResourceToStores(H2ToDistrubute);
    }
}
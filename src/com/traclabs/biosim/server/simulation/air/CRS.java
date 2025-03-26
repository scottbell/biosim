package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.framework.MalfunctionIntensity;
import com.traclabs.biosim.server.framework.MalfunctionLength;
import com.traclabs.biosim.server.simulation.framework.SimBioModule;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.server.simulation.power.PowerConsumerOperations;
import com.traclabs.biosim.server.simulation.water.PotableWaterProducerDefinition;
import com.traclabs.biosim.server.simulation.water.PotableWaterProducerOperations;

public class CRS extends SimBioModule implements CRSOperations,
        PowerConsumerOperations, PotableWaterProducerOperations,
        O2ProducerOperations, CO2ConsumerOperations, H2ConsumerOperations, MethaneProducerOperations {
    //Consumers, Producers
    private PowerConsumerDefinition myPowerConsumerDefinition;

    private PotableWaterProducerDefinition myPotableWaterProducerDefinition;

    private O2ProducerDefinition myO2ProducerDefinition;

    private CO2ConsumerDefinition myCO2ConsumerDefinition;

    private H2ConsumerDefinition myH2ConsumerDefinition;

    private MethaneProducerDefinition myMethaneProducerDefinition;

    private float currentPowerConsumed = 0f;

    private float currentCO2Consumed;

    private float currentH2Consumed;

    private float currentH2OProduced;

    private float currentCH4Produced;

    //multiply times power to determine how much air/H2/water we're consuming
    private static final float LINEAR_MULTIPLICATIVE_FACTOR = 0.02777777777777778f;

    public CRS(int pID, String pName) {
        super(pID, pName);
        myPowerConsumerDefinition = new PowerConsumerDefinition(this);
        myPotableWaterProducerDefinition = new PotableWaterProducerDefinition(this);
        myO2ProducerDefinition = new O2ProducerDefinition(this);
        myCO2ConsumerDefinition = new CO2ConsumerDefinition(this);
        myH2ConsumerDefinition = new H2ConsumerDefinition(this);
        myMethaneProducerDefinition = new MethaneProducerDefinition(this);
    }

    public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinition.getCorbaObject();
    }

    public PotableWaterProducerDefinition getPotableWaterProducerDefinition() {
        return myPotableWaterProducerDefinition.getCorbaObject();
    }

    public CO2ConsumerDefinition getCO2ConsumerDefinition() {
        return myCO2ConsumerDefinition.getCorbaObject();
    }

    public O2ProducerDefinition getO2ProducerDefinition() {
        return myO2ProducerDefinition.getCorbaObject();
    }

    public H2ConsumerDefinition getH2ConsumerDefinition() {
        return myH2ConsumerDefinition.getCorbaObject();
    }

    public MethaneProducerDefinition getMethaneProducerDefinition() {
        return myMethaneProducerDefinition.getCorbaObject();
    }

    private void gatherPower() {
        currentPowerConsumed = myPowerConsumerDefinition
                .getMostResourceFromStores();
    }

    /**
     * Processes a tick by collecting referernces (if needed), resources, and
     * pushing the new air out.
     */
    public void tick() {
        super.tick();
        gatherPower();
        gatherH2andCO2();
        pushWaterAndMethane();
    }

    private void gatherH2andCO2() {
        float CO2Needed = currentPowerConsumed * LINEAR_MULTIPLICATIVE_FACTOR * getTickLength();
        float H2Needed = CO2Needed * 4f;
        currentCO2Consumed = myCO2ConsumerDefinition
                .getResourceFromStores(CO2Needed);
        currentH2Consumed = myH2ConsumerDefinition
                .getResourceFromStores(H2Needed);
    }

    private void pushWaterAndMethane() {
        if ((currentH2Consumed <= 0) || (currentCO2Consumed <= 0)) {
            currentH2OProduced = 0f;
            currentCH4Produced = 0f;
            myH2ConsumerDefinition.pushResourceToStores(currentH2Consumed);
            myCO2ConsumerDefinition.pushResourceToStores(currentCO2Consumed);
        } else {
            // CO2 + 4H2 --> CH4 + 2H20
            float limitingReactant = Math.min(currentH2Consumed / 4f,
                    currentCO2Consumed);
            if (limitingReactant == currentH2Consumed)
                myCO2ConsumerDefinition
                        .pushResourceToStores(currentCO2Consumed
                                - limitingReactant);
            else
                myH2ConsumerDefinition
                        .pushResourceToStores(currentH2Consumed - 4f
                                * limitingReactant);
            float waterMolesProduced = 2f * limitingReactant;
            float waterLitersProduced = (waterMolesProduced * 18.01524f) / 1000f; //1000g/liter,
            // 18.01524g/mole
            float methaneMolesProduced = limitingReactant;
            currentH2OProduced = waterLitersProduced;
            currentCH4Produced = methaneMolesProduced;
        }
        myPotableWaterProducerDefinition
                .pushResourceToStores(currentH2OProduced);
        myMethaneProducerDefinition
                .pushResourceToStores(currentCH4Produced);
    }

    protected String getMalfunctionName(MalfunctionIntensity pIntensity,
            MalfunctionLength pLength) {
        return "None";
    }

    protected void performMalfunctions() {
    }
    
    public void log() {
        myLogger.debug("power_consumed=" + currentPowerConsumed);
    }

    /**
     * Resets production/consumption levels.
     */
    public void reset() {
        super.reset();
        myPowerConsumerDefinition.reset();
        myPotableWaterProducerDefinition.reset();
        myO2ProducerDefinition.reset();
        myCO2ConsumerDefinition.reset();
        myH2ConsumerDefinition.reset();
        myMethaneProducerDefinition.reset();
    }

}
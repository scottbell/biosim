package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.air.AirRSOperations;
import com.traclabs.biosim.idl.simulation.air.Breath;
import com.traclabs.biosim.idl.simulation.framework.AirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.AirProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.AirProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.CO2ConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.CO2ConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.CO2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.CO2ProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.H2ConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.H2ConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.H2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.H2ProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.O2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.O2ProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.PowerConsumerOperations;
import com.traclabs.biosim.server.simulation.framework.AirConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.AirProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.CO2ConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.CO2ProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.H2ConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.H2ProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.O2ProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.PotableWaterConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.PotableWaterProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.PowerConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;

/**
 * The Air Revitalization System Implementation. Takes in Air (O2, CO2, other)
 * from the environment and power from the Power Production System and produces
 * air with less CO2 and more O2.
 * 
 * @author Scott Bell
 */

public class AirRSLinearImpl extends SimBioModuleImpl implements
        AirRSOperations, PowerConsumerOperations,
        PotableWaterConsumerOperations, PotableWaterProducerOperations,
        AirConsumerOperations, O2ProducerOperations, AirProducerOperations,
        CO2ProducerOperations, CO2ConsumerOperations, H2ProducerOperations,
        H2ConsumerOperations {

    //Consumers, Producers
    private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;

    private PotableWaterConsumerDefinitionImpl myPotableWaterConsumerDefinitionImpl;

    private PotableWaterProducerDefinitionImpl myPotableWaterProducerDefinitionImpl;

    private AirConsumerDefinitionImpl myAirConsumerDefinitionImpl;

    private AirProducerDefinitionImpl myAirProducerDefinitionImpl;

    private O2ProducerDefinitionImpl myO2ProducerDefinitionImpl;

    private CO2ConsumerDefinitionImpl myCO2ConsumerDefinitionImpl;

    private CO2ProducerDefinitionImpl myCO2ProducerDefinitionImpl;

    private H2ProducerDefinitionImpl myH2ProducerDefinitionImpl;

    private H2ConsumerDefinitionImpl myH2ConsumerDefinitionImpl;

    private Breath myCurrentBreath = new Breath(0f, 0f, 0f, 0f, 0f);

    private float currentPowerConsumed = 0f;

    private float currentCO2Produced = 0f;

    private float currentCO2Consumed;

    private float currentH2Consumed;

    private float currentH2OProduced;

    private float currentCH4Produced;

    private float CH4Produced = 0f;

    private float currentH2OConsumed;

    private float currentO2Produced;

    private float currentH2Produced;

    //multiply times power to determine how much air/H2/water we're consuming
    private static final float LINEAR_MULTIPLICATIVE_FACTOR = 100;

    public AirRSLinearImpl(int pID, String pName) {
        super(pID, pName);
        myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl();
        myPotableWaterConsumerDefinitionImpl = new PotableWaterConsumerDefinitionImpl();
        myPotableWaterProducerDefinitionImpl = new PotableWaterProducerDefinitionImpl();
        myAirConsumerDefinitionImpl = new AirConsumerDefinitionImpl();
        myAirProducerDefinitionImpl = new AirProducerDefinitionImpl();
        myO2ProducerDefinitionImpl = new O2ProducerDefinitionImpl();
        myCO2ConsumerDefinitionImpl = new CO2ConsumerDefinitionImpl();
        myCO2ProducerDefinitionImpl = new CO2ProducerDefinitionImpl();
        myH2ProducerDefinitionImpl = new H2ProducerDefinitionImpl();
        myH2ConsumerDefinitionImpl = new H2ConsumerDefinitionImpl();
    }

    public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinitionImpl.getCorbaObject();
    }

    public PotableWaterConsumerDefinition getPotableWaterConsumerDefinition() {
        return myPotableWaterConsumerDefinitionImpl.getCorbaObject();
    }

    public PotableWaterProducerDefinition getPotableWaterProducerDefinition() {
        return myPotableWaterProducerDefinitionImpl.getCorbaObject();
    }

    public AirConsumerDefinition getAirConsumerDefinition() {
        return myAirConsumerDefinitionImpl.getCorbaObject();
    }

    public AirProducerDefinition getAirProducerDefinition() {
        return myAirProducerDefinitionImpl.getCorbaObject();
    }

    public CO2ProducerDefinition getCO2ProducerDefinition() {
        return myCO2ProducerDefinitionImpl.getCorbaObject();
    }

    public CO2ConsumerDefinition getCO2ConsumerDefinition() {
        return myCO2ConsumerDefinitionImpl.getCorbaObject();
    }

    public O2ProducerDefinition getO2ProducerDefinition() {
        return myO2ProducerDefinitionImpl.getCorbaObject();
    }

    public H2ProducerDefinition getH2ProducerDefinition() {
        return myH2ProducerDefinitionImpl.getCorbaObject();
    }

    public H2ConsumerDefinition getH2ConsumerDefinition() {
        return myH2ConsumerDefinitionImpl.getCorbaObject();
    }

    private void gatherPower() {
        currentPowerConsumed = myPowerConsumerDefinitionImpl
                .getMostResourceFromStore();
    }

    /**
     * Processes a tick by collecting referernces (if needed), resources, and
     * pushing the new air out.
     */
    public void tick() {
        super.tick();
        myLogger.debug("tick");
        gatherPower();
        gatherAir();
        pushAir();
        gatherH2andCO2();
        pushWaterAndMethane();
        gatherWater();
        pushOxygen();
    }

    private void gatherAir() {
        float gatheredAir = 0f;
        float gatheredO2 = 0f;
        float gatheredCO2 = 0f;
        float gatheredOther = 0f;
        float gatheredWater = 0f;
        float gatheredNitrogen = 0f;
        float airNeeded = currentPowerConsumed * LINEAR_MULTIPLICATIVE_FACTOR;
        myCurrentBreath = myAirConsumerDefinitionImpl
                .getAirFromEnvironment(airNeeded);
    }

    private void pushAir() {
        Breath breathToDistribute = new Breath(myCurrentBreath.O2, 0f,
                myCurrentBreath.water, myCurrentBreath.other,
                myCurrentBreath.nitrogen);
        Breath breathDistributed = myAirProducerDefinitionImpl
                .pushAirToEnvironments(breathToDistribute);
        currentCO2Produced = myCurrentBreath.CO2;
        float distributedCO2Left = myCO2ProducerDefinitionImpl
                .pushResourceToStore(currentCO2Produced);
    }

    private void gatherH2andCO2() {
        float CO2Needed = currentPowerConsumed * LINEAR_MULTIPLICATIVE_FACTOR;
        float H2Needed = CO2Needed * 4f;
        float filteredCO2Needed = randomFilter(CO2Needed);
        float filteredH2Needed = randomFilter(H2Needed);
        currentCO2Consumed = myCO2ConsumerDefinitionImpl
                .getResourceFromStore(filteredCO2Needed);
        currentH2Consumed = myH2ConsumerDefinitionImpl
                .getResourceFromStore(filteredH2Needed);
    }

    private void pushWaterAndMethane() {
        if ((currentH2Consumed <= 0) || (currentCO2Consumed <= 0)) {
            currentH2OProduced = 0f;
            currentCH4Produced = 0f;
            myH2ConsumerDefinitionImpl.pushResourceToStore(currentH2Consumed);
            myCO2ConsumerDefinitionImpl.pushResourceToStore(currentCO2Consumed);
        } else {
            // CO2 + 4H2 --> CH4 + 2H20
            float limitingReactant = Math.min(currentH2Consumed / 4f,
                    currentCO2Consumed);
            if (limitingReactant == currentH2Consumed)
                myCO2ConsumerDefinitionImpl
                        .pushResourceToStore(currentCO2Consumed
                                - limitingReactant);
            else
                myH2ConsumerDefinitionImpl
                        .pushResourceToStore(currentH2Consumed - 4f
                                * limitingReactant);
            float waterMolesProduced = 2f * limitingReactant;
            float waterLitersProduced = (waterMolesProduced * 18.01524f) / 1000f; //1000g/liter,
            // 18.01524g/mole
            float methaneMolesProduced = limitingReactant;
            currentH2OProduced = randomFilter(waterLitersProduced);
            currentCH4Produced = randomFilter(methaneMolesProduced);
        }
        float distributedWaterLeft = myPotableWaterProducerDefinitionImpl
                .pushResourceToStore(currentH2OProduced);
        CH4Produced += currentCH4Produced;
    }

    private void gatherWater() {
        float waterNeeded = currentPowerConsumed * LINEAR_MULTIPLICATIVE_FACTOR;
        currentH2OConsumed = myPotableWaterConsumerDefinitionImpl
                .getResourceFromStore(waterNeeded);
    }

    private void pushOxygen() {
        //2H20 --> 2H2 + O2
        float molesOfWater = (currentH2OConsumed * 1000f) / 18.01524f; //1000g/liter,
        // 18.01524g/mole
        float molesOfReactant = molesOfWater / 2f;
        currentO2Produced = randomFilter(molesOfReactant);
        currentH2Produced = randomFilter(molesOfReactant * 2f);
        float O2ToDistrubute = randomFilter(currentO2Produced);
        float H2ToDistrubute = randomFilter(currentH2Produced);
        float distributedO2 = myO2ProducerDefinitionImpl
                .pushResourceToStore(O2ToDistrubute);
        float distributedH2 = myH2ProducerDefinitionImpl
                .pushResourceToStore(H2ToDistrubute);
    }

    protected String getMalfunctionName(MalfunctionIntensity pIntensity,
            MalfunctionLength pLength) {
        return "None";
    }

    protected void performMalfunctions() {
    }

    /**
     * Resets production/consumption levels.
     */
    public void reset() {
        super.reset();
        CH4Produced = 0f;
    }
}
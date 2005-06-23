package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.air.CO2ConsumerDefinition;
import com.traclabs.biosim.idl.simulation.air.CO2ConsumerOperations;
import com.traclabs.biosim.idl.simulation.air.CRSOperations;
import com.traclabs.biosim.idl.simulation.air.H2ConsumerDefinition;
import com.traclabs.biosim.idl.simulation.air.H2ConsumerOperations;
import com.traclabs.biosim.idl.simulation.air.MethaneProducerDefinition;
import com.traclabs.biosim.idl.simulation.air.MethaneProducerOperations;
import com.traclabs.biosim.idl.simulation.air.O2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.air.O2ProducerOperations;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerOperations;
import com.traclabs.biosim.idl.simulation.water.PotableWaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.water.PotableWaterProducerOperations;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.PotableWaterProducerDefinitionImpl;

/**
 * The Air Revitalization System Implementation. Takes in Air (O2, CO2, other)
 * from the environment and power from the Power Production System and produces
 * air with less CO2 and more O2.
 * 
 * @author Scott Bell
 */

public class CRSImpl extends SimBioModuleImpl implements CRSOperations,
        PowerConsumerOperations, PotableWaterProducerOperations,
        O2ProducerOperations, CO2ConsumerOperations, H2ConsumerOperations, MethaneProducerOperations {
    //Consumers, Producers
    private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;

    private PotableWaterProducerDefinitionImpl myPotableWaterProducerDefinitionImpl;

    private O2ProducerDefinitionImpl myO2ProducerDefinitionImpl;

    private CO2ConsumerDefinitionImpl myCO2ConsumerDefinitionImpl;

    private H2ConsumerDefinitionImpl myH2ConsumerDefinitionImpl;

    private MethaneProducerDefinitionImpl myMethaneProducerDefinitionImpl;

    private float currentPowerConsumed = 0f;

    private float currentCO2Consumed;

    private float currentH2Consumed;

    private float currentH2OProduced;

    private float currentCH4Produced;

    //multiply times power to determine how much air/H2/water we're consuming
    private static final float LINEAR_MULTIPLICATIVE_FACTOR = 100;

    public CRSImpl(int pID, String pName) {
        super(pID, pName);
        myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl();
        myPotableWaterProducerDefinitionImpl = new PotableWaterProducerDefinitionImpl();
        myO2ProducerDefinitionImpl = new O2ProducerDefinitionImpl();
        myCO2ConsumerDefinitionImpl = new CO2ConsumerDefinitionImpl();
        myH2ConsumerDefinitionImpl = new H2ConsumerDefinitionImpl();
        myMethaneProducerDefinitionImpl = new MethaneProducerDefinitionImpl();
    }

    public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinitionImpl.getCorbaObject();
    }

    public PotableWaterProducerDefinition getPotableWaterProducerDefinition() {
        return myPotableWaterProducerDefinitionImpl.getCorbaObject();
    }

    public CO2ConsumerDefinition getCO2ConsumerDefinition() {
        return myCO2ConsumerDefinitionImpl.getCorbaObject();
    }

    public O2ProducerDefinition getO2ProducerDefinition() {
        return myO2ProducerDefinitionImpl.getCorbaObject();
    }

    public H2ConsumerDefinition getH2ConsumerDefinition() {
        return myH2ConsumerDefinitionImpl.getCorbaObject();
    }

    public MethaneProducerDefinition getMethaneProducerDefinition() {
        return myMethaneProducerDefinitionImpl.getCorbaObject();
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
        gatherPower();
        gatherH2andCO2();
        pushWaterAndMethane();
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
        myPotableWaterProducerDefinitionImpl
                .pushResourceToStore(currentH2OProduced);
        myMethaneProducerDefinitionImpl
                .pushResourceToStore(currentCH4Produced);
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
    }

}
package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.idl.simulation.air.OGSOperations;
import com.traclabs.biosim.idl.simulation.framework.H2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.H2ProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.O2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.O2ProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.PowerConsumerOperations;
import com.traclabs.biosim.server.simulation.framework.H2ProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.O2ProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.PotableWaterConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.PowerConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;

/**
 * The Air Revitalization System Implementation. Takes in Air (O2, CO2, other)
 * from the environment and power from the Power Production System and produces
 * air with less CO2 and more O2.
 * 
 * @author Scott Bell
 */

public class OGSImpl extends SimBioModuleImpl implements OGSOperations,
        PowerConsumerOperations, PotableWaterConsumerOperations,
        O2ProducerOperations, H2ProducerOperations {

    //Consumers, Producers
    private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;

    private PotableWaterConsumerDefinitionImpl myPotableWaterConsumerDefinitionImpl;

    private O2ProducerDefinitionImpl myO2ProducerDefinitionImpl;

    private H2ProducerDefinitionImpl myH2ProducerDefinitionImpl;

    private float currentH2OConsumed = 0;

    private float currentO2Produced = 0;

    private float currentH2Produced = 0;

    private float myProductionRate = 1f;

    private float currentPowerConsumed = 0f;

    public OGSImpl(int pID, String pName) {
        super(pID, pName);
        myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl();
        myPotableWaterConsumerDefinitionImpl = new PotableWaterConsumerDefinitionImpl();
        myO2ProducerDefinitionImpl = new O2ProducerDefinitionImpl();
        myH2ProducerDefinitionImpl = new H2ProducerDefinitionImpl();
    }

    public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinitionImpl.getCorbaObject();
    }

    public PotableWaterConsumerDefinition getPotableWaterConsumerDefinition() {
        return myPotableWaterConsumerDefinitionImpl.getCorbaObject();
    }

    public O2ProducerDefinition getO2ProducerDefinition() {
        return myO2ProducerDefinitionImpl.getCorbaObject();
    }

    public H2ProducerDefinition getH2ProducerDefinition() {
        return myH2ProducerDefinitionImpl.getCorbaObject();
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
    }

    private void gatherPower() {
        currentPowerConsumed = myPowerConsumerDefinitionImpl
                .getMostResourceFromStore();
        myLogger.debug("currentPowerConsumed = " + currentPowerConsumed);
    }

    public void tick() {
        super.tick();
        gatherPower();
        gatherWater();
        pushGasses();
    }

    private void gatherWater() {
        float waterToConsume = (currentPowerConsumed / 75f) * 0.04167f;
        currentH2OConsumed = myPotableWaterConsumerDefinitionImpl
                .getResourceFromStore(waterToConsume);
        myLogger.debug("currentH2OConsumed = " + currentH2OConsumed);
    }

    private void pushGasses() {
        //2H20 --> 2H2 + O2
        float molesOfWater = (currentH2OConsumed * 1000f) / 18.01524f; //1000g/liter,
        // 18.01524g/mole
        float molesOfReactant = molesOfWater / 2f;
        currentO2Produced = randomFilter(molesOfReactant) * myProductionRate;
        currentH2Produced = randomFilter(molesOfReactant * 2f)
                * myProductionRate;
        float O2ToDistrubute = randomFilter(currentO2Produced);
        float H2ToDistrubute = randomFilter(currentH2Produced);
        float distributedO2 = myO2ProducerDefinitionImpl
                .pushResourceToStore(O2ToDistrubute);
        myLogger.debug("O2ToDistrubute = " + O2ToDistrubute);
        float distributedH2 = myH2ProducerDefinitionImpl
                .pushResourceToStore(H2ToDistrubute);
    }
}
package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.idl.simulation.air.CO2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.air.CO2ProducerOperations;
import com.traclabs.biosim.idl.simulation.air.VCCROperations;
import com.traclabs.biosim.idl.simulation.environment.Air;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.environment.AirProducerDefinition;
import com.traclabs.biosim.idl.simulation.environment.AirProducerOperations;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerOperations;
import com.traclabs.biosim.server.simulation.environment.AirConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.environment.AirProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinitionImpl;

/**
 * Produces air with less CO2.
 * 
 * @author Scott Bell
 */

public class VCCRLinearImpl extends SimBioModuleImpl implements VCCROperations,
        PowerConsumerOperations, AirConsumerOperations, AirProducerOperations,
        CO2ProducerOperations {

    //Consumers, Producers
    private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;

    private AirConsumerDefinitionImpl myAirConsumerDefinitionImpl;

    private AirProducerDefinitionImpl myAirProducerDefinitionImpl;

    private CO2ProducerDefinitionImpl myCO2ProducerDefinitionImpl;

    private float currentCO2Produced = 0f;

    private float currentPowerConsumed = 0;
    
    private float gatheredCO2 = 0;

    public VCCRLinearImpl(int pID, String pName) {
        super(pID, pName);
        myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl(this);
        myAirConsumerDefinitionImpl = new AirConsumerDefinitionImpl(this);
        myAirProducerDefinitionImpl = new AirProducerDefinitionImpl(this);
        myCO2ProducerDefinitionImpl = new CO2ProducerDefinitionImpl(this);
    }

    public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinitionImpl.getCorbaObject();
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

    /**
     * Processes a tick by collecting referernces (if needed), resources, and
     * pushing the new air out.
     */
    public void tick() {
        super.tick();
        gatherPower();
        gatherCO2();
        pushCO2();
    }

    /**
     * Adds power for this tick
     */
    private void gatherPower() {
        currentPowerConsumed = myPowerConsumerDefinitionImpl
                .getMostResourceFromStores();
    }

    /**
     * Resets production/consumption levels.
     */
    public void reset() {
        super.reset();
        currentPowerConsumed = 0;
        currentCO2Produced = 0f;
        myPowerConsumerDefinitionImpl.reset();
        myAirConsumerDefinitionImpl.reset();
        myAirProducerDefinitionImpl.reset();
        myCO2ProducerDefinitionImpl.reset();
    }

    private void gatherCO2() {
        //25.625 watts -> 1.2125 moles of air per tick
    	//roughly 0.02844 moles of CO2 per tick
        float molesAirNeeded = (currentPowerConsumed / 25.625f) * 1.2125f  * 100 * getTickLength();
        SimEnvironment theEnvironment = myAirConsumerDefinitionImpl.getEnvironments()[0];
        Air airConsumed = myAirConsumerDefinitionImpl.getAirFromEnvironment(molesAirNeeded, 0);
    	gatheredCO2 = airConsumed.co2Moles;
    	airConsumed.co2Moles = 0f;
    	myAirProducerDefinitionImpl.pushAirToEnvironment(airConsumed, 0);
    }
    
    public void log() {
        myLogger.debug("power_consumed=" + currentPowerConsumed);
    }

    private void pushCO2() {
        //distrbute CO2
        currentCO2Produced = gatheredCO2;
        myCO2ProducerDefinitionImpl
                .pushResourceToStores(currentCO2Produced);
    }
}
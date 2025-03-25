package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.simulation.air.CO2ProducerDefinition;
import com.traclabs.biosim.server.simulation.air.CO2ProducerOperations;
import com.traclabs.biosim.server.simulation.air.VCCROperations;
import com.traclabs.biosim.server.simulation.environment.Air;
import com.traclabs.biosim.server.simulation.environment.AirConsumerDefinition;
import com.traclabs.biosim.server.simulation.environment.AirConsumerOperations;
import com.traclabs.biosim.server.simulation.environment.AirProducerDefinition;
import com.traclabs.biosim.server.simulation.environment.AirProducerOperations;
import com.traclabs.biosim.server.simulation.environment.SimEnvironment;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.server.simulation.power.PowerConsumerOperations;
import com.traclabs.biosim.server.simulation.environment.AirConsumerDefinition;
import com.traclabs.biosim.server.simulation.environment.AirProducerDefinition;
import com.traclabs.biosim.server.simulation.framework.SimBioModule;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinition;

/**
 * Produces air with less CO2.
 * 
 * @author Scott Bell
 */

public class VCCRLinear extends SimBioModule implements VCCROperations,
        PowerConsumerOperations, AirConsumerOperations, AirProducerOperations,
        CO2ProducerOperations {

    //Consumers, Producers
    private PowerConsumerDefinition myPowerConsumerDefinition;

    private AirConsumerDefinition myAirConsumerDefinition;

    private AirProducerDefinition myAirProducerDefinition;

    private CO2ProducerDefinition myCO2ProducerDefinition;

    private float currentCO2Produced = 0f;

    private float currentPowerConsumed = 0;
    
    private float gatheredCO2 = 0;

    public VCCRLinear(int pID, String pName) {
        super(pID, pName);
        myPowerConsumerDefinition = new PowerConsumerDefinition(this);
        myAirConsumerDefinition = new AirConsumerDefinition(this);
        myAirProducerDefinition = new AirProducerDefinition(this);
        myCO2ProducerDefinition = new CO2ProducerDefinition(this);
    }

    public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinition.getCorbaObject();
    }

    public AirConsumerDefinition getAirConsumerDefinition() {
        return myAirConsumerDefinition.getCorbaObject();
    }

    public AirProducerDefinition getAirProducerDefinition() {
        return myAirProducerDefinition.getCorbaObject();
    }

    public CO2ProducerDefinition getCO2ProducerDefinition() {
        return myCO2ProducerDefinition.getCorbaObject();
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
        currentPowerConsumed = myPowerConsumerDefinition
                .getMostResourceFromStores();
    }

    /**
     * Resets production/consumption levels.
     */
    public void reset() {
        super.reset();
        currentPowerConsumed = 0;
        currentCO2Produced = 0f;
        myPowerConsumerDefinition.reset();
        myAirConsumerDefinition.reset();
        myAirProducerDefinition.reset();
        myCO2ProducerDefinition.reset();
    }

    private void gatherCO2() {
        //25.625 watts -> 1.2125 moles of air per tick
    	//roughly 0.02844 moles of CO2 per tick
        float molesAirNeeded = (currentPowerConsumed / 25.625f) * 1.2125f  * 100 * getTickLength();
        SimEnvironment theEnvironment = myAirConsumerDefinition.getEnvironments()[0];
        Air airConsumed = myAirConsumerDefinition.getAirFromEnvironment(molesAirNeeded, 0);
    	gatheredCO2 = airConsumed.co2Moles;
    	airConsumed.co2Moles = 0f;
    	myAirProducerDefinition.pushAirToEnvironment(airConsumed, 0);
    }
    
    public void log() {
        myLogger.debug("power_consumed=" + currentPowerConsumed);
    }

    private void pushCO2() {
        //distrbute CO2
        currentCO2Produced = gatheredCO2;
        myCO2ProducerDefinition
                .pushResourceToStores(currentCO2Produced);
    }
}
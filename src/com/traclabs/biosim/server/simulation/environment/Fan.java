package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.server.framework.MalfunctionIntensity;
import com.traclabs.biosim.server.framework.MalfunctionLength;
import com.traclabs.biosim.server.simulation.environment.Air;
import com.traclabs.biosim.server.simulation.environment.AirConsumerDefinition;
import com.traclabs.biosim.server.simulation.environment.AirConsumerOperations;
import com.traclabs.biosim.server.simulation.environment.AirProducerDefinition;
import com.traclabs.biosim.server.simulation.environment.AirProducerOperations;
import com.traclabs.biosim.server.simulation.environment.FanOperations;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.server.simulation.power.PowerConsumerOperations;
import com.traclabs.biosim.server.simulation.framework.SimBioModule;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinition;

/**
 * The basic Fan ementation.
 * 
 * @author Scott Bell
 */

public class Fan extends SimBioModule implements FanOperations, AirConsumerOperations, PowerConsumerOperations, AirProducerOperations {
    //Consumers, Producers
    private AirConsumerDefinition myAirConsumerDefinition;
    private PowerConsumerDefinition myPowerConsumerDefinition;

    private AirProducerDefinition myAirProducerDefinition;
    
    //  in kPA assuming 101 kPa total pressure and air temperature of 23C and relative humidity of 80%
    public static final float OPTIMAL_MOISTURE_CONCENTRATION = 0.0218910f;

    private float currentPowerConsumed = 0f;

    private float currentMolesOfAirConsumed = 0f;

    private Air currentAirConsumed;

    public Fan(int pID, String pName) {
        super(pID, pName);
        myAirConsumerDefinition = new AirConsumerDefinition(this);
        myPowerConsumerDefinition = new PowerConsumerDefinition(this);
        myAirProducerDefinition = new AirProducerDefinition(this);
    }
    
    public Fan() {
        this(0, "Unnamed Fan");
    }

    public AirConsumerDefinition getAirConsumerDefinition() {
        return myAirConsumerDefinition.getCorbaObject();
    }

    public AirProducerDefinition getAirProducerDefinition() {
        return myAirProducerDefinition.getCorbaObject();
    }

    public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinition.getCorbaObject();
    }

    public void tick() {
        super.tick();
        getAndPushAir();
    }

    private void getAndPushAir() {
    	currentPowerConsumed = myPowerConsumerDefinition.getMostResourceFromStores();
    	currentMolesOfAirConsumed = calculateAirToConsume(currentPowerConsumed);
    	currentAirConsumed = myAirConsumerDefinition.getAirFromEnvironment(currentMolesOfAirConsumed, 0);
    	myAirProducerDefinition.pushAirToEnvironment(currentAirConsumed, 0);
    }

    private float calculateAirToConsume(float powerReceived) {
		return powerReceived * 4;
	}

	protected String getMalfunctionName(MalfunctionIntensity pIntensity,
            MalfunctionLength pLength) {
        StringBuffer returnBuffer = new StringBuffer();
        if (pIntensity == MalfunctionIntensity.SEVERE_MALF)
            returnBuffer.append("Severe ");
        else if (pIntensity == MalfunctionIntensity.MEDIUM_MALF)
            returnBuffer.append("Medium ");
        else if (pIntensity == MalfunctionIntensity.LOW_MALF)
            returnBuffer.append("Low ");
        if (pLength == MalfunctionLength.TEMPORARY_MALF)
            returnBuffer.append("Temporary Production Reduction");
        else if (pLength == MalfunctionLength.PERMANENT_MALF)
            returnBuffer.append("Permanent Production Reduction");
        return returnBuffer.toString();
    }
    
    public void log() {
        myLogger.debug("power_consumed=" + currentPowerConsumed);
    }

    protected void performMalfunctions() {
    }

    public void reset() {
        super.reset();
        myAirConsumerDefinition.reset();
        myAirProducerDefinition.reset();
        myPowerConsumerDefinition.reset();
    }
}
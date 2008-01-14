package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.environment.Air;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.environment.AirProducerDefinition;
import com.traclabs.biosim.idl.simulation.environment.AirProducerOperations;
import com.traclabs.biosim.idl.simulation.environment.FanOperations;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerOperations;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinitionImpl;

/**
 * The basic Fan Implementation.
 * 
 * @author Scott Bell
 */

public class FanImpl extends SimBioModuleImpl implements FanOperations, AirConsumerOperations, PowerConsumerOperations, AirProducerOperations {
    //Consumers, Producers
    private AirConsumerDefinitionImpl myAirConsumerDefinitionImpl;
    private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;

    private AirProducerDefinitionImpl myAirProducerDefinitionImpl;
    
    //  in kPA assuming 101 kPa total pressure and air temperature of 23C and relative humidity of 80%
    public static final float OPTIMAL_MOISTURE_CONCENTRATION = 0.0218910f;

    private float currentPowerConsumed = 0f;

    private float currentMolesOfAirConsumed = 0f;

    private Air currentAirConsumed;

    public FanImpl(int pID, String pName) {
        super(pID, pName);
        myAirConsumerDefinitionImpl = new AirConsumerDefinitionImpl(this);
        myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl(this);
        myAirProducerDefinitionImpl = new AirProducerDefinitionImpl(this);
    }
    
    public FanImpl() {
        this(0, "Unnamed Fan");
    }

    public AirConsumerDefinition getAirConsumerDefinition() {
        return myAirConsumerDefinitionImpl.getCorbaObject();
    }

    public AirProducerDefinition getAirProducerDefinition() {
        return myAirProducerDefinitionImpl.getCorbaObject();
    }

    public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinitionImpl.getCorbaObject();
    }

    public void tick() {
        super.tick();
        getAndPushAir();
    }

    private void getAndPushAir() {
    	currentPowerConsumed = myPowerConsumerDefinitionImpl.getMostResourceFromStores();
    	currentMolesOfAirConsumed = calculateAirToConsume(currentPowerConsumed);
    	currentAirConsumed = myAirConsumerDefinitionImpl.getAirFromEnvironment(currentMolesOfAirConsumed, 0);
    	myAirProducerDefinitionImpl.pushAirToEnvironment(currentAirConsumed, 0);
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
        myAirConsumerDefinitionImpl.reset();
        myAirProducerDefinitionImpl.reset();
        myPowerConsumerDefinitionImpl.reset();
    }
}
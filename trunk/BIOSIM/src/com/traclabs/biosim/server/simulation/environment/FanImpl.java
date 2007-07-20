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
    
    public static final float OPTIMAL_MOISTURE_CONCENTRATION = 0.0218910f;
    //  in kPA assuming 101 kPa total pressure and air temperature of 23C and relative humidity of 80%

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
    	float powerReceived = myPowerConsumerDefinitionImpl.getMostResourceFromStores();
    	float molesOfAirToConsume = calculateAirToConsume(powerReceived);
    	Air airConsumed = myAirConsumerDefinitionImpl.getAirFromEnvironment(molesOfAirToConsume, 0);
    	myAirProducerDefinitionImpl.pushAirToEnvironment(airConsumed, 0);
    }

    private float calculateAirToConsume(float powerReceived) {
		return powerReceived / 100;
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

    protected void performMalfunctions() {
    }

    public void reset() {
        super.reset();
        myAirConsumerDefinitionImpl.reset();
        myAirProducerDefinitionImpl.reset();
        myPowerConsumerDefinitionImpl.reset();
    }

    public void log() {
    }
}
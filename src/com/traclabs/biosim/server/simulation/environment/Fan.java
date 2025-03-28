package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.server.framework.MalfunctionIntensity;
import com.traclabs.biosim.server.framework.MalfunctionLength;
import com.traclabs.biosim.server.simulation.framework.SimBioModule;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinition;

/**
 * The basic Fan implementation.
 *
 * @author Scott Bell
 */

public class Fan extends SimBioModule {
    //  in kPA assuming 101 kPa total pressure and air temperature of 23C and relative humidity of 80%
    public static final float OPTIMAL_MOISTURE_CONCENTRATION = 0.0218910f;
    //Consumers, Producers
    private final AirConsumerDefinition myAirConsumerDefinition;
    private final PowerConsumerDefinition myPowerConsumerDefinition;
    private final AirProducerDefinition myAirProducerDefinition;
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
        return myAirConsumerDefinition;
    }

    public AirProducerDefinition getAirProducerDefinition() {
        return myAirProducerDefinition;
    }

    public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinition;
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
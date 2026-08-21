package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.server.framework.MalfunctionIntensity;
import com.traclabs.biosim.server.framework.MalfunctionLength;
import com.traclabs.biosim.server.simulation.framework.SimBioModule;
import com.traclabs.biosim.server.simulation.power.PowerConsumer;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinition;

/**
 * The basic Fan implementation.
 *
 * @author Scott Bell
 */

public class Fan extends SimBioModule implements AirConsumer, PowerConsumer, AirProducer {
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

    /**
     * Air moved this tick from the source environment's P and T.
     * XML flow rates are per hour; moles this tick = (Q * P / (R T)) * tickLength.
     * Q scales linearly with commanded power: Q = Q_ref * (W / W_ref).
     * Zero watts moves no air. Power remains the command (not RPM).
     *
     * W_ref = 50 W, Q_ref = 36000 L/h. At 55 kPa / 23 °C that is about 804 mol/h.
     * The old watts*4 rule ignored P, T, and tickLength, so a one-minute tick
     * moved a full hour of air.
     */
    static final float W_REF_WATTS = 50f;
    static final float Q_REF_LITERS_PER_HOUR = 36000f;
    private static final float R_KPA_LITERS = 8.314f;

    static float molesThisTick(float powerWatts, float pressureKpa,
                               float temperatureKelvin, float tickLengthHours) {
        if (powerWatts <= 0f || pressureKpa <= 0f || temperatureKelvin <= 0f
                || tickLengthHours <= 0f)
            return 0f;
        float qLitersPerHour = Q_REF_LITERS_PER_HOUR * (powerWatts / W_REF_WATTS);
        float molesPerHour = qLitersPerHour * pressureKpa
                / (R_KPA_LITERS * temperatureKelvin);
        return molesPerHour * tickLengthHours;
    }

    private float calculateAirToConsume(float powerReceived) {
        SimEnvironment[] sources = myAirConsumerDefinition.getEnvironments();
        if (sources == null || sources.length < 1 || sources[0] == null)
            return 0f;
        SimEnvironment src = sources[0];
        return molesThisTick(powerReceived, src.getTotalPressure(),
                src.getTemperatureInKelvin(), getTickLength());
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
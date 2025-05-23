package com.traclabs.biosim.server.simulation.water.aws;

import com.traclabs.biosim.server.simulation.water.WaterRS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The abstract class all the water subsystems derive from (the AES, BWP, PPS,
 * and RO).
 *
 * @author Scott Bell
 */

public abstract class WaterRSSubSystem {
    //The power consumed (in watts) by this water subsystem at this tick
    // (default)
    protected float currentPowerConsumed = 0;

    //During any given tick, this much power (in watts) is needed for a water
    // subsystem (default)
    protected float basePowerNeeded = 100;

    protected float currentPowerNeeded;

    //During any given tick, this much water (in liters) is needed for a water
    // subsystem (default)
    //Pete says WRS consumes/produces 16 mL/s -> 57.6 L/H
    protected float waterNeeded = 57.6f;

    //Reference to the WaterRS to get other watersubsystems
    protected WaterRS myWaterRS;

    //Flag to determine whether the water subsystem has received enough power
    // for this tick
    protected boolean hasEnoughPower = false;

    //Flag to determine whether the water subsystem has received enough water
    // for this tick
    protected boolean hasEnoughWater = false;

    //Amount of water in this subsystem at the current tick
    protected float waterLevel = 0;

    protected boolean enabled = true;
    protected Logger myLogger;
    private boolean malfunctioning = false;

    /**
     * Constructor that creates the subsystem
     *
     * @param pWaterRS The Water RS system this subsystem is contained in
     */
    public WaterRSSubSystem(WaterRS pWaterRS) {
        myWaterRS = pWaterRS;
        myLogger = LoggerFactory.getLogger(this.getClass());
        currentPowerNeeded = basePowerNeeded * myWaterRS.getTickLength();
    }

    /**
     * Resets power consumption and water levels.
     */
    public void reset() {
        hasEnoughPower = false;
        hasEnoughWater = false;
        waterLevel = 0;
        currentPowerConsumed = 0f;
        malfunctioning = false;
        enabled = true;
    }

    public float getPowerConsumed() {
        return currentPowerConsumed;
    }

    /**
     * Returns whether the water subsytem has enough power at the current tick.
     *
     * @return <code>true</code> if the water subsytem has enough power to
     * function, <code>false</code> if not
     */
    public boolean hasPower() {
        return hasEnoughPower;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean pEnabled) {
        if (!malfunctioning)
            enabled = pEnabled;
        if (enabled)
            currentPowerNeeded = 0;
        else
            currentPowerNeeded = basePowerNeeded * myWaterRS.getTickLength();
    }

    public boolean isMalfunctioning() {
        return malfunctioning;
    }

    public void setMalfunctioning(boolean pMalfunctioning) {
        malfunctioning = pMalfunctioning;
        if (malfunctioning)
            enabled = false;
    }

    /**
     * Returns whether the water subsytem has enough water at the current tick.
     *
     * @return <code>true</code> if the water subsytem has enough water to
     * function, <code>false</code> if not
     */
    public boolean hasWater() {
        return hasEnoughWater;
    }

    /**
     * Adds power to the subsystem for this tick
     */
    protected void gatherPower() {
        float gatheredPower = 0f;
        gatheredPower = myWaterRS.getPowerConsumerDefinition()
                .getFractionalResourceFromStores(currentPowerNeeded,
                        1f / myWaterRS.getSubsystemsConsumingPower());
        currentPowerConsumed = gatheredPower;
        hasEnoughPower = !(currentPowerConsumed < currentPowerNeeded);
    }

    /**
     * Adds water to the subsystem for this tick
     *
     * @param pWater the amount of water to add (in liters)
     */
    public void addWater(float pWater) {
        waterLevel += pWater;
        hasEnoughWater = !(waterLevel < 0);
    }

    /**
     * Tick does nothing by default
     */
    public void tick() {
        if (enabled)
            gatherPower();
        else
            currentPowerConsumed = 0f;
        if (myLogger.isDebugEnabled())
            log();
    }

    public void log() {
        myLogger.debug("enabled=" + enabled);
        myLogger.debug("basePowerNeeded=" + basePowerNeeded);
        myLogger.debug("currentPowerNeeded=" + currentPowerNeeded);
        myLogger.debug("power_consumed=" + currentPowerConsumed);
        myLogger.debug("water_needed=" + waterNeeded);
        myLogger.debug("has_enough_power=" + hasEnoughPower);
        myLogger.debug("has_enough_water=" + hasEnoughWater);
        myLogger.debug("water_level=" + waterLevel);
    }

    /**
     * @return Returns the basePowerNeeded.
     */
    public float getBasePowerNeeded() {
        return basePowerNeeded;
    }
}
package com.traclabs.biosim.server.simulation.water;

import org.apache.log4j.Logger;

/**
 * The abstract class all the water subsystems derive from (the AES, BWP, PPS,
 * and RO).
 * 
 * @author Scott Bell
 */

public abstract class WaterRSSubSystem {
    //The power consumed (in watts) by this water subsystem at this tick
    // (default)
    float currentPowerConsumed = 0;

    //During any given tick, this much power (in watts) is needed for a water
    // subsystem (default)
    float powerNeeded = 100;

    //During any given tick, this much water (in liters) is needed for a water
    // subsystem (default)
    //Pete says WRS consumes/produces 16 mL/s -> 57.6 L/H
    float waterNeeded = 57.6f;

    //Reference to the WaterRS to get other watersubsystems
    WaterRSImpl myWaterRS;

    //Flag to determine whether the water subsystem has received enough power
    // for this tick
    boolean hasEnoughPower = false;

    //Flag to determine whether the water subsystem has received enough water
    // for this tick
    boolean hasEnoughWater = false;

    //Amount of water in this subsystem at the current tick
    float waterLevel = 0;

    private boolean logInitialized = false;

    boolean enabled = true;

    private boolean malfunctioning = false;
    
    protected Logger myLogger;

    /**
     * Constructor that creates the subsystem
     * 
     * @param pWaterRSImpl
     *            The Water RS system this subsystem is contained in
     */
    public WaterRSSubSystem(WaterRSImpl pWaterRSImpl) {
        myWaterRS = pWaterRSImpl;
        myLogger = Logger.getLogger(this.getClass());
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
     *         function, <code>false</code> if not
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
    }

    public void setMalfunctioning(boolean pMalfunctioning) {
        malfunctioning = pMalfunctioning;
        if (malfunctioning)
            enabled = false;
    }

    public boolean isMalfunctioning() {
        return malfunctioning;
    }

    /**
     * Returns whether the water subsytem has enough water at the current tick.
     * 
     * @return <code>true</code> if the water subsytem has enough water to
     *         function, <code>false</code> if not
     */
    public boolean hasWater() {
        return hasEnoughWater;
    }

    /**
     * Adds power to the subsystem for this tick
     */
    protected void gatherPower() {
        float gatheredPower = 0f;
        for (int i = 0; (i < myWaterRS.getPowerInputs().length)
                && (gatheredPower < powerNeeded); i++) {
            float powerToGatherFirst = Math.min(powerNeeded, (myWaterRS
                    .getPowerInputMaxFlowRate(i) / myWaterRS
                    .getSubsystemsConsumingPower()));
            float powerToGatherFinal = Math.min(powerToGatherFirst, (myWaterRS
                    .getPowerInputDesiredFlowRate(i) / myWaterRS
                    .getSubsystemsConsumingPower()));
            myWaterRS
                    .addPowerInputActualFlowRate(
                            (myWaterRS.getPowerInputs())[i]
                                    .take(powerToGatherFinal), i);
            gatheredPower += myWaterRS.getPowerInputActualFlowRate(i);
        }
        currentPowerConsumed = gatheredPower;
        if (currentPowerConsumed < powerNeeded) {
            hasEnoughPower = false;
        } else {
            hasEnoughPower = true;
        }
    }

    /**
     * Adds water to the subsystem for this tick
     * 
     * @param pWater
     *            the amount of water to add (in liters)
     */
    public void addWater(float pWater) {
        waterLevel += myWaterRS.randomFilter(pWater);
        if (waterLevel < 0) {
            hasEnoughWater = false;
        } else {
            hasEnoughWater = true;
        }
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
        myLogger.debug("enabled="+enabled);
        myLogger.debug("power_needed="+powerNeeded);
        myLogger.debug("power_consumed="+currentPowerConsumed);
        myLogger.debug("water_needed="+waterNeeded);
        myLogger.debug("has_enough_power="+hasEnoughPower);
        myLogger.debug("has_enough_water="+hasEnoughWater);
        myLogger.debug("water_level="+waterLevel);
    }

}
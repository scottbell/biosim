package com.traclabs.biosim.server.simulation.air;

import org.apache.log4j.Logger;

/**
 * The abstract class all the air subsystems derive from (the VCCR, CRS, OGS,
 * ...)
 * 
 * @author Scott Bell
 */

public abstract class AirRSSubSystem {
    //The power consumed (in watts) by this air subsystem at this tick
    // (default)
    protected float currentPowerConsumed = 0;

    //During any given tick, this much power (in watts) is needed for a air
    // subsystem (default)
    float currentPowerNeeded;

    float basePowerNeeded = 100;

    protected AirRSImpl myAirRS;

    //Flag to determine whether the air subsystem has received enough power for
    // this tick
    protected boolean hasEnoughPower = false;

    private boolean logInitialized = false;

    boolean enabled = true;

    protected Logger myLogger;

    public AirRSSubSystem(AirRSImpl pAirRSImpl) {
        myAirRS = pAirRSImpl;
        myLogger = Logger.getLogger(this.getClass());
        currentPowerNeeded = basePowerNeeded;
    }

    public abstract void reset();

    public float getPowerConsumed() {
        return currentPowerConsumed;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean pEnabled) {
        if (enabled)
            currentPowerNeeded = 0;
        else
            currentPowerNeeded = basePowerNeeded;
    }

    /**
     * Returns whether the air subsytem has enough power at the current tick.
     * 
     * @return <code>true</code> if the air subsytem has enough power to
     *         function, <code>false</code> if not
     */
    public boolean hasPower() {
        return hasEnoughPower;
    }

    /**
     * Adds power to the subsystem for this tick
     */
    protected void gatherPower() {
        float gatheredPower = myAirRS.getFractionalResourceFromStore(myAirRS
                .getPowerInputs(), myAirRS.getPowerInputMaxFlowRates(), myAirRS
                .getPowerInputDesiredFlowRates(), myAirRS
                .getPowerInputActualFlowRates(), currentPowerNeeded,
                1f / myAirRS.getSubsystemsConsumingPower());
        if (gatheredPower < currentPowerNeeded)
            hasEnoughPower = false;
        else
            hasEnoughPower = true;
    }

    public void tick() {
        gatherPower();
        if (myLogger.isDebugEnabled())
            log();
    }

    public void log() {
        myLogger.debug("power_needed=" + currentPowerNeeded);
    }

}
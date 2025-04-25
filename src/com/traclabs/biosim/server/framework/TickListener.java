package com.traclabs.biosim.server.framework;

/**
 * Interface for objects that want to be notified when a BioDriver tick occurs.
 */
public interface TickListener {
    /**
     * Called when a tick occurs in a BioDriver.
     *
     * @param simID     The ID of the simulation that ticked
     * @param tickCount The current tick count after the tick
     */
    void tickOccurred(int simID, int tickCount);
}

package com.traclabs.biosim.server.simulation.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Scott Bell
 */

public class SimpleBuffer {
    private static final Logger myLogger = LoggerFactory.getLogger(SimpleBuffer.class);
    //The level of whatever this store is holding (at t)
    protected float level = 0f;
    //The capacity of what this store can hold (at t)
    protected float capacity = 0f;
    //What this store has leaked (at t)
    protected float overflow = 0f;
    protected float initialLevel = 0f;
    protected float initialCapacity = 0f;

    /**
     * Creates a Store with an initial level and capacity of 0
     */
    public SimpleBuffer() {
        setInitialLevelAndCapacity(0, 10);
    }

    /**
     * Creates a Store with an initial level and capacity user specified
     *
     * @param pInitialLevel    the initial level of the store
     * @param pInitialCapacity the initial capacity of the store
     */
    public SimpleBuffer(float pInitialLevel, float pInitialCapacity) {
        setInitialLevelAndCapacity(pInitialLevel, pInitialCapacity);
    }

    public void setInitialLevelAndCapacity(float pInitialLevel, float pInitialCapacity) {
        setCapacity(pInitialCapacity);
        setLevel(pInitialLevel);
        initialCapacity = getCapacity();
        initialLevel = getLevel();
    }

    /**
     * Attempts to add to the store. If the level is near capacity, it will only
     * up to capacity
     *
     * @param amountRequested the amount wanted to add to the store
     * @return the amount actually added to the store
     */
    public float add(float amountRequested) {
        //idiot check
        if (amountRequested <= 0)
            return 0f;
        float acutallyAdded = 0f;
        if ((amountRequested + level) > capacity) {
            //adding more than capacity
            acutallyAdded = capacity - level;
            level += acutallyAdded;
            overflow += (amountRequested - acutallyAdded);
            return acutallyAdded;
        }
        acutallyAdded = amountRequested;
        level += acutallyAdded;
        return acutallyAdded;
    }

    /**
     * Attemps to take the amount requested from the store
     *
     * @param amountRequested the amount wanted
     * @return the amount actually retrieved
     */
    public float take(float amountRequested) {
        //idiot check
        if (amountRequested <= 0f) {
            return 0f;
        }
        float takenAmount;
        //asking for more stuff than exists
        if (amountRequested > level) {
            takenAmount = level;
            level = 0f;
        }
        //stuff exists for request
        else {
            takenAmount = amountRequested;
            level -= takenAmount;
        }
        return takenAmount;
    }

    /**
     * Retrieves the level of the store
     *
     * @return the level of the store
     */
    public float getLevel() {
        return level;
    }

    /**
     * Sets the level to a set amount
     *
     * @param metricAmount the level to set the store to
     */
    public void setLevel(float metricAmount) {
        level = Math.min(metricAmount, capacity);
    }

    /**
     * Retrieves the overflow of the store
     *
     * @return the overflow of the store
     */
    public float getOverflow() {
        return overflow;
    }

    public float getAmountMissing() {
        return capacity - level;
    }

    /**
     * Retrieves the capacity of the store
     *
     * @return the capacity of the store
     */
    public float getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity of the store (how much it can hold)
     *
     * @param newCapacity the new volume of the store
     */
    public void setCapacity(float newCapacity) {
        if (newCapacity <= 0) {
            //myLogger.debug("told to change capacity to <= 0");
            setCapacity(Float.MIN_VALUE);
        }
        float percentage = level / capacity;
        //myLogger.debug("SimpleBuffer: level = " + level + ", capacity ="
        ///        + capacity + " percentage = " + percentage);
        capacity = newCapacity;
        level = percentage * newCapacity;
        // myLogger.debug(", newCapacity = " + newCapacity + ", newLevel = "
        //       + level);
    }

    /**
     * Resets the level to 0
     */
    public void reset() {
        level = initialLevel;
        capacity = initialCapacity;
        overflow = 0f;
    }
}
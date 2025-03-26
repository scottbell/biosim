package com.traclabs.biosim.server.framework;

import java.util.concurrent.atomic.AtomicLong;

public class Malfunction {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);
    
    private final long id;
    private final String name;
    private final MalfunctionIntensity intensity;
    private final MalfunctionLength length;
    private boolean performed = false;
    private int repairWorkDone = 0;
    private int repairWorkNeeded = 0;
    private long tickToMalfunction = 0;
    private float tickLength = 0f;

    /**
     * Constructor
     * 
     * @param pName The name of the malfunction
     * @param pIntensity The intensity of the malfunction
     * @param pLength The length of the malfunction
     * @param pTickLength The tick length
     */
    public Malfunction(String pName, MalfunctionIntensity pIntensity,
            MalfunctionLength pLength, float pTickLength) {
        this.id = ID_GENERATOR.incrementAndGet();
        this.name = pName;
        this.intensity = pIntensity;
        this.length = pLength;
        this.tickLength = pTickLength;
        
        // Determine how much repair work is needed based on intensity
        if (pIntensity == MalfunctionIntensity.SEVERE_MALF)
            repairWorkNeeded = 10;
        else if (pIntensity == MalfunctionIntensity.MEDIUM_MALF)
            repairWorkNeeded = 5;
        else if (pIntensity == MalfunctionIntensity.LOW_MALF)
            repairWorkNeeded = 2;
        else
            repairWorkNeeded = 1;
    }

    /**
     * Gets the intensity of the malfunction
     * 
     * @return The malfunction intensity
     */
    public MalfunctionIntensity getIntensity() {
        return intensity;
    }

    /**
     * Gets the ID of the malfunction
     * 
     * @return The malfunction ID
     */
    public long getID() {
        return id;
    }

    /**
     * Gets the name of the malfunction
     * 
     * @return The malfunction name
     */
    public String getName() {
        return name;
    }

    /**
     * Checks if the malfunction has been performed
     * 
     * @return true if the malfunction has been performed, false otherwise
     */
    public boolean hasPerformed() {
        return performed;
    }

    /**
     * Sets whether the malfunction has been performed
     * 
     * @param performed true if the malfunction has been performed, false otherwise
     */
    public void setPerformed(boolean performed) {
        this.performed = performed;
    }

    /**
     * Gets the length of the malfunction
     * 
     * @return The malfunction length
     */
    public MalfunctionLength getLength() {
        return length;
    }

    /**
     * Performs some repair work on the malfunction
     */
    public void doSomeRepairWork() {
        repairWorkDone++;
    }

    /**
     * Checks if enough repair work has been done to fix the malfunction
     * 
     * @return true if enough repair work has been done, false otherwise
     */
    public boolean doneEnoughRepairWork() {
        return repairWorkDone >= repairWorkNeeded;
    }

    /**
     * Gets the tick at which the malfunction should occur
     * 
     * @return The tick at which the malfunction should occur
     */
    public long getTickToMalfunction() {
        return tickToMalfunction;
    }

    /**
     * Sets the tick at which the malfunction should occur
     * 
     * @param tickToMalfunction The tick at which the malfunction should occur
     */
    public void setTickToMalfunction(long tickToMalfunction) {
        this.tickToMalfunction = tickToMalfunction;
    }

    /**
     * Resets the malfunction
     */
    public void reset() {
        repairWorkDone = 0;
        performed = false;
    }
}

package com.traclabs.biosim.server.simulation.framework;

import java.util.Iterator;

import com.traclabs.biosim.server.framework.Malfunction;
import com.traclabs.biosim.server.framework.MalfunctionIntensity;
import com.traclabs.biosim.server.framework.MalfunctionLength;
import com.traclabs.biosim.server.simulation.framework.StoreOperations;

/**
 * The basic Store ementation. Allows for basic store functionality (like
 * adding, removing). <br>
 * Stores report information about their levels, etc. from currentTick-1 until
 * ALL modules have advanced to currentTick. <br>
 * This allows for simulated parralelism.
 * 
 * @author Scott Bell
 */

public class Store extends PassiveModule implements
        StoreOperations {
    //The currentLevel of whatever this store is holding (at t)
    protected float currentLevel = 0f;

    //The currentCapacity of what this store can hold (at t)
    protected float currentCapacity = 0f;
    
    //What this store has leaked (at t)
    protected float overflow = 0f;

    private boolean pipe = false;

    protected float initialLevel = 0f;

    protected float initialCapacity = 0f;

    private int resupplyFrequency = 0;

    private float resupplyAmount = 0f;

    /**
     * Creates a Store with an initial currentLevel and currentCapacity of 0
     * 
     * @param pID
     *            the ID of the store
     * @param pName
     *            the name of the store
     */
    public Store(int pID, String pName) {
        super(pID, pName);
        currentLevel  = initialLevel = 100f;
        currentCapacity = initialCapacity = 100f;
    }

    /**
     * Creates a Store with an initial currentLevel and currentCapacity user
     * specified
     * 
     * @param pID
     *            the initial currentLevel of the store
     * @param pName
     *            the name of the store
     * @param pInitialCapacity
     *            the initial currentCapacity of the store
     * @param pInitialLevel
     *            the initial currentLevel of the store
     * @param pPipe
     *            whether this store should act like a pipe. dynamic capcity ==
     *            currentLevel == whatever is added THIS tick (0 if nothing
     *            added, maxFlowRate should dictate pipe size, infinite
     *            otherwise)
     */
    public Store(int pID, String pName, float pInitialLevel,
            float pInitialCapacity, boolean pPipe) {
        super(pID, pName);
        pipe = pPipe;
        currentLevel = initialLevel = pInitialLevel;
        currentCapacity = initialCapacity = pInitialCapacity;
    }

    public Store() {
		this(0, "Unnamed Store");
	}

	/**
     * If this store acts like a pipe. dynamic capcity == currentLevel ==
     * whatever is added THIS tick (0 if nothing added, maxFlowRate should
     * dictate pipe size, infinite otherwise)
     * 
     * @return pPipe whether this store acts like a pipe.
     */
    public boolean isPipe() {
        return pipe;
    }

    /**
     * Sets this store to act like a pipe. dynamic capcity == currentLevel ==
     * whatever is added THIS tick (0 if nothing added, maxFlowRate should
     * dictate pipe size, infinite otherwise)
     * 
     * @param pPipe
     *            whether this store should act like a pipe.
     */
    public void setPipe(boolean pPipe) {
        pipe = pPipe;
    }

    public void setResupply(int pResupplyFrequency, float pResupplyAmount) {
        resupplyFrequency = pResupplyFrequency;
        resupplyAmount = pResupplyAmount;
    }

    public void setInitialCapacity(float metricAmount) {
        initialCapacity = metricAmount;
        setCurrentCapacity(metricAmount);
        if (currentLevel > initialCapacity)
        	setInitialLevel(initialCapacity);
    }

    /**
     * Sets the currentCapacity of the store (how much it can hold)
     * 
     * @param metricAmount
     *            the new volume of the store
     */
    public void setCurrentCapacity(float metricAmount) {
        currentCapacity = metricAmount;
    }

    /**
     * Sets the currentLevel to a set amount
     * 
     * @param metricAmount
     *            the currentLevel to set the store to
     */
    public void setCurrentLevel(float metricAmount) {
        currentLevel = metricAmount;
    }

    public void setInitialLevel(float metricAmount) {
        initialLevel = metricAmount;
        setCurrentLevel(metricAmount);
    }

    public void tick() {
        super.tick();
        if ((getMyTicks() > 0) && (resupplyFrequency > 0)) {
            int remainder = getMyTicks() % resupplyFrequency;
            if (remainder == 0) {
                add(resupplyAmount);
            }
        }
        if (pipe) {
            currentLevel = 0f;
            currentCapacity = 0f;
        }
    }

    /**
     * Gives a decent name/description of the malfunction as it relates to this
     * module.
     * 
     * @param pIntensity
     *            The intensity of the malfunction (severe, medium, low)
     * @param pLength
     *            The temporal length of the malfunction (temporary, permanent)
     * @return the description/name of the malfunction
     */
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
            returnBuffer.append("Leak");
        else if (pLength == MalfunctionLength.PERMANENT_MALF)
            returnBuffer.append("Capacity Reduction");
        return returnBuffer.toString();
    }

    /**
     * Actually performs the malfunctions. Reduces levels/currentCapacity
     */
    protected void performMalfunctions() {
        for (Iterator iter = myMalfunctions.values().iterator(); iter.hasNext();) {
            Malfunction currentMalfunction = (Malfunction) (iter.next());
            if (currentMalfunction.getLength() == MalfunctionLength.TEMPORARY_MALF) {
                float leakRate = 0f;
                if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
                    leakRate = .20f * getTickLength();
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
                	leakRate = .10f * getTickLength();
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
                	leakRate = .05f * getTickLength();
                currentLevel -= (currentLevel * leakRate);
                currentMalfunction.setPerformed(true);
            } else if ((currentMalfunction.getLength() == MalfunctionLength.PERMANENT_MALF)
                    && (!currentMalfunction.hasPerformed())) {
                float percentage;
                if (currentCapacity <= 0) {
                    currentLevel = 0;
                    percentage = 0;
                    currentMalfunction.setPerformed(true);
                    return;
                }
                percentage = currentLevel / currentCapacity;
                if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
                    currentCapacity = 0f;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
                    currentCapacity *= 0.25;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
                    currentCapacity *= .50f;
                currentLevel = percentage * currentCapacity;
                currentMalfunction.setPerformed(true);
            }
        }
    }

    /**
     * Attempts to add to the store. If the currentLevel is near
     * currentCapacity, it will only up to currentCapacity
     * 
     * @param amountRequested
     *            the amount wanted to add to the store
     * @return the amount actually added to the store
     */
    public float add(float amountRequested) {
        //idiot check
        if (Float.isNaN(amountRequested)) {
            myLogger
                    .warn(getModuleName()
                            + ": warning, in add(), attemped to add "
                            + amountRequested);
            return 0f;
        }
        if (amountRequested <= 0)
            return 0f;
        if (pipe)
            currentCapacity += amountRequested;
        float acutallyAdded = 0f;
        if ((amountRequested + currentLevel) > currentCapacity) {
            //adding more than currentCapacity
            acutallyAdded = currentCapacity - currentLevel;
            currentLevel += acutallyAdded;
            overflow += (amountRequested - acutallyAdded);
            return acutallyAdded;
        }
		acutallyAdded = amountRequested;
		currentLevel += acutallyAdded;
		return acutallyAdded;
    }

    /**
     * Attemps to take the amount requested from the store
     * 
     * @param amountRequested
     *            the amount wanted
     * @return the amount actually retrieved
     */
    public float take(float amountRequested) {
        if (pipe)
            currentCapacity -= amountRequested;
        //idiot check
        if (Float.isNaN(amountRequested)) {
            myLogger
                    .warn(getModuleName()
                            + ": warning, in take(), attemped to add "
                            + amountRequested);
            return 0f;
        }
        if (amountRequested <= 0)
            return 0f;
        float takenAmount;
        //asking for more stuff than exists
        if (amountRequested > currentLevel) {
            takenAmount = getStochasticFilter().randomFilter(currentLevel);
            currentLevel = 0f;
        }
        //stuff exists for request
        else {
            takenAmount = getStochasticFilter().randomFilter(amountRequested);
            currentLevel -= takenAmount;
        }
        return takenAmount;
    }

    /**
     * Retrieves the currentLevel of the store
     * 
     * @return the currentLevel of the store
     */
    public float getCurrentLevel() {
		return currentLevel;
    }

    /**
     * Retrieves the overflow of the store
     * 
     * @return the overflow of the store
     */
    public float getOverflow() {
		return overflow;
    }

    /**
     * Retrieves the currentCapacity of the store
     * 
     * @return the currentCapacity of the store
     */
    public float getCurrentCapacity() {
		return currentCapacity;
    }

    /**
     * Resets the currentLevel to 0
     */
    public void reset() {
        super.reset();
        currentLevel = initialLevel;
        currentCapacity = initialCapacity;
        overflow = 0f;
    }

    private boolean cachedValueNeeded() {
        //collectReferences();
        //return (getMyTicks() == myDriver.getTicks());
        return true;
    }

    /**
     * Logs this store
     */
    public void log() {
        myLogger.debug("currentLevel=" + currentLevel);
        myLogger.debug("currentCapacity="+ currentCapacity);
        myLogger.debug("overflow=" + overflow);
    }

    /**
     * @return Returns the initialCapacity.
     */
    public float getInitialCapacity() {
        return initialCapacity;
    }

    /**
     * @return Returns the initialLevel.
     */
    public float getInitialLevel() {
        return initialLevel;
    }

	public float getPercentageFilled() {
		if (currentCapacity <= 0)
			return 1f;
		return currentLevel / currentCapacity;
	}
}
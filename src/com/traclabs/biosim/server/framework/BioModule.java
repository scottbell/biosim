package com.traclabs.biosim.server.framework;

import ch.qos.logback.classic.Level;
import com.traclabs.biosim.server.util.failure.FailureDecider;
import com.traclabs.biosim.server.util.stochastic.NoFilter;
import com.traclabs.biosim.server.util.stochastic.StochasticFilter;
import com.traclabs.biosim.util.MersenneTwister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * The BioModule implementation. Every Module should derive from this as to
 * allow ticking and logging.
 *
 * @author Scott Bell
 */

public abstract class BioModule implements IBioModule {
    protected Map<Long, Malfunction> myMalfunctions;
    protected List<Malfunction> myScheduledMalfunctions;
    protected Logger myLogger;
    private StochasticFilter myStochasticFilter = new NoFilter();
    //The unique ID for this module (all the modules this module communicates
    // with should have the same ID)
    private int myID = 0;
    //The name of this module (should be the same in the nameserver)
    private String myName = "NoName";
    private boolean canFail = false;
    private boolean hasFailed = false;
    //What I think the current tick is.
    private int myTicks = 0;
    private int myFailureTime = 0;
    //in hours (can be a fraction of an hour)
    private float myTickInterval = 1f;
    private FailureDecider myFailureDecider;

    /**
     * Constructor to create a BioModule, should only be called by those
     * deriving from BioModule.
     *
     * @param pID   The unique ID for this module (all the modules this module
     *              communicates with should have the same ID)
     * @param pName The name of the module
     */
    protected BioModule() {
        this(0, "Unnamed");
    }

    /**
     * Constructor to create a BioModule, should only be called by those
     * deriving from BioModule.
     *
     * @param pID   The unique ID for this module (all the modules this module
     *              communicates with should have the same ID)
     * @param pName The name of the module
     */
    protected BioModule(int pID, String pName) {
        myLogger = LoggerFactory.getLogger(this.getClass() + "." + pName);
        myMalfunctions = new Hashtable<Long, Malfunction>();
        myScheduledMalfunctions = new Vector<Malfunction>();
        myName = pName;
        myID = pID;
    }

    /**
     * Called at every tick of the simulation. Does nothing if not overriden.
     */
    public void tick() {
        checkForScheduledMalfunctions();
        if (canFail && !hasFailed)
            checkForFailure();
        if (isMalfunctioning())
            performMalfunctions();
        if (myLogger.isDebugEnabled())
            log();
        myTicks++;
        myFailureTime++;
    }

    public int getMyTicks() {
        return myTicks;
    }

    private void checkForFailure() {
        if (myFailureDecider.hasFailed(myFailureTime)) {
            myLogger.info(getModuleName() + " has failed on tick " + getMyTicks());
            hasFailed = true;
            startMalfunction(MalfunctionIntensity.SEVERE_MALF, MalfunctionLength.PERMANENT_MALF);
        }
    }

    private void checkForScheduledMalfunctions() {
        for (Malfunction currentMalfunction : myScheduledMalfunctions) {
            if (currentMalfunction.getTickToMalfunction() == getMyTicks()) {
                myLogger.info("😬 " + getModuleName() + " has malfunctioned on tick " + getMyTicks());
                startMalfunction(currentMalfunction);
            }
        }
    }

    protected void performMalfunctions() {
    }

    public void log() {
    }


    /**
     * Fixes all malfunctions. Permanent malfunctions are unfixable.
     */
    public void fixAllMalfunctions() {
        for (Malfunction currentMalfunction : myMalfunctions.values()) {
            if (currentMalfunction.getLength() == MalfunctionLength.TEMPORARY_MALF)
                myMalfunctions.remove(Long.valueOf(currentMalfunction.getID()));
        }
        if (myMalfunctions.isEmpty()) {
            reset();
        }
    }

    /**
     * Clears all malfunctions regardless of temporal length (i.e., permanent
     * malfunctions removed)
     */
    public void clearAllMalfunctions() {
        for (Malfunction currentMalfunction : myMalfunctions.values())
            myMalfunctions.remove(currentMalfunction.getID());
        myFailureTime = 0;
        if (myMalfunctions.isEmpty()) {
            reset();
        }
    }

    /**
     * Tells the names of the malfunctions currently active with this module
     *
     * @return An array of String containing the names of the malfunctions
     * currently active with this module
     */
    public String[] getMalfunctionNames() {
        String[] malfunctionNames = new String[myMalfunctions.size()];
        int i = 0;
        for (Malfunction currentMalfunction : myMalfunctions.values()) {
            malfunctionNames[i] = currentMalfunction.getName();
            i++;
        }
        return malfunctionNames;
    }

    /**
     * Tells the malfunctions currently active with this module
     *
     * @return An array of malfunctions currently active with this module
     */
    public Malfunction[] getMalfunctions() {
        Malfunction[] arrayMalfunctions = new Malfunction[myMalfunctions.size()];
        return (myMalfunctions.values()
                .toArray(arrayMalfunctions));
    }

    /**
     * Fixes a malfunction currently active with this module. The malfunction
     * must be "repaired" a certain number of times depending on the
     * intensity/severity of the malfunction.
     *
     * @param malfunctionID the ID of the malfunction to repair
     */
    public void doSomeRepairWork(long malfunctionID) {
        Malfunction currentMalfunction = (myMalfunctions
                .get(malfunctionID));
        if (currentMalfunction == null)
            return;
        currentMalfunction.doSomeRepairWork();
        if (currentMalfunction.doneEnoughRepairWork())
            fixMalfunction(currentMalfunction.getID());
    }

    /**
     * Returns a nice description of the malfunction. The default implementation
     * (provided here) should be overriden in the specific modules (i.e.,
     * temporary low mafunction means a small leak in the Store modules)
     *
     * @param pIntensity the intensity of the malfunction
     * @param pLength    the temporal length of the malfunction
     * @return the description/name of the malfunction specified
     */
    protected String getMalfunctionName(MalfunctionIntensity pIntensity,
                                        MalfunctionLength pLength) {
        StringBuffer returnBuffer = new StringBuffer();
        if (pLength == MalfunctionLength.TEMPORARY_MALF)
            returnBuffer.append("Temporary ");
        else if (pLength == MalfunctionLength.PERMANENT_MALF)
            returnBuffer.append("Permanent ");
        if (pIntensity == MalfunctionIntensity.SEVERE_MALF)
            returnBuffer.append("Severe ");
        else if (pIntensity == MalfunctionIntensity.MEDIUM_MALF)
            returnBuffer.append("Medium ");
        else if (pIntensity == MalfunctionIntensity.LOW_MALF)
            returnBuffer.append("Low ");
        returnBuffer.append("Malfunction");
        return returnBuffer.toString();
    }

    /**
     * Starts a malfunction in this module.
     *
     * @param pIntensity the intensity of the malfunction
     * @param pLength    the temporal length of the malfunction
     * @return the malfunction started
     */
    public Malfunction startMalfunction(MalfunctionIntensity pIntensity,
                                        MalfunctionLength pLength) {
        String malfunctionName = getMalfunctionName(pIntensity, pLength);
        Malfunction newMalfunction = new Malfunction(
                malfunctionName, pIntensity, pLength, getTickLength());
        myMalfunctions.put(newMalfunction.getID(), newMalfunction);
        return newMalfunction;
    }

    /**
     * Starts a malfunction in this module.
     *
     * @param pMalfunction the malfunction
     */
    private void startMalfunction(Malfunction pMalfunction) {
        myMalfunctions.put(pMalfunction.getID(), pMalfunction);
    }

    /**
     * Schedules a malfunction in this module.
     *
     * @param pIntensity the intensity of the malfunction
     * @param pLength    the temporal length of the malfunction
     * @return the malfunction started
     */
    public void scheduleMalfunction(MalfunctionIntensity pIntensity,
                                    MalfunctionLength pLength, int tickToOccur) {
        String malfunctionName = getMalfunctionName(pIntensity, pLength);
        Malfunction newMalfunction = new Malfunction(
                malfunctionName, pIntensity, pLength, getTickLength());
        newMalfunction.setTickToMalfunction(tickToOccur);
        myScheduledMalfunctions.add(newMalfunction);
    }

    /**
     * Fixes a temporary malfunction currently active with this module
     * instantly.
     *
     * @param pID the ID of the malfunction to remove
     */
    public void fixMalfunction(long pID) {
        Malfunction theMalfunction = (myMalfunctions
                .get(pID));
        if (theMalfunction.getLength() == MalfunctionLength.TEMPORARY_MALF)
            myMalfunctions.remove(pID);
        if (myMalfunctions.isEmpty()) {
            reset();
        }
    }

    /**
     * Clears a malfunction currently active with this module regardless of
     * temporal length (i.e., permanent malfunctions removed)
     *
     * @param malfunctionID the ID of the malfunction to remove
     */
    public void clearMalfunction(long malfunctionID) {
        myMalfunctions.remove(malfunctionID);
    }

    /**
     * Tells if this module is malfunctioning (i.e. has > 0 malfunctions)
     *
     * @return if this module is malfunctioning (i.e. has > 0 malfunctions),
     * returns <code>true</code>, else <code>false</code>
     */
    public boolean isMalfunctioning() {
        return (myMalfunctions.size() > 0);
    }

    /**
     * Resets this module (should be called by extended classes as it clears
     * malfunctions)
     */
    public void reset() {
        myTicks = 0;
        myFailureTime = 0;
        hasFailed = false;
        if (myFailureDecider != null)
            myFailureDecider.reset();
        if (myStochasticFilter != null)
            myStochasticFilter.reset();
        myMalfunctions.clear();
        for (Malfunction currentMalfunction : myScheduledMalfunctions)
            currentMalfunction.reset();
    }

    /**
     * Tells The ID of this module. Should be the same as every other module in
     * this BioSim instance
     *
     * @return The ID of this module. Should be the same as every other module
     * in this BioSim instance
     */
    public int getID() {
        return myID;
    }

    public void setEnableFailure(boolean pValue) {
        canFail = pValue;
        myTicks = 0;
        myFailureTime = 0;
        hasFailed = false;
    }

    public boolean isFailureEnabled() {
        return canFail;
    }

    public void maintain() {
        //does nothing right now
    }

    public void setLogLevel(Level level) {
        if (myLogger instanceof ch.qos.logback.classic.Logger lbLogger) {
            lbLogger.setLevel(level);
        } else {
            myLogger.warn("Cannot set log level for logger of type " + myLogger.getClass().getName());
        }
    }

    public float randomFilter(float value) {
        return myStochasticFilter.randomFilter(value);
    }

    public StochasticFilter getStochasticFilter() {
        return myStochasticFilter;
    }

    public void setStochasticFilter(StochasticFilter filter) {
        this.myStochasticFilter = filter;
    }

    /**
     * Returns the name of the module, "Unamed" if not overriden
     *
     * @return the name of this module
     */
    public String getModuleName() {
        return myName;
    }

    /**
     * Sets the name of the module
     *
     * @return the name of this module
     */
    public void setModuleName(String pName) {
        myName = pName;
    }

    public float getTickLength() {
        return myTickInterval;
    }

    public void setTickLength(float pInterval) {
        myTickInterval = pInterval;
    }

    public void setFailureDecider(FailureDecider decider) {
        this.myFailureDecider = decider;
    }
}
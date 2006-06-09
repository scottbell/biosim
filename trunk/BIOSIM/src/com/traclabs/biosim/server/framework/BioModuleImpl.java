package com.traclabs.biosim.server.framework;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.traclabs.biosim.idl.framework.BioModulePOA;
import com.traclabs.biosim.idl.framework.LogLevel;
import com.traclabs.biosim.idl.framework.Malfunction;
import com.traclabs.biosim.idl.framework.MalfunctionHelper;
import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.framework.StochasticIntensity;
import com.traclabs.biosim.server.util.MathUtils;
import com.traclabs.biosim.server.util.failure.FailureDecider;
import com.traclabs.biosim.util.OrbUtils;

/**
 * The BioModule Implementation. Every Module should derive from this as to
 * allow ticking and logging.
 * 
 * @author Scott Bell
 */

public abstract class BioModuleImpl extends BioModulePOA {
    //The random number generator used for gaussian function (stochastic stuff)
    private Random myRandomGen;

    //The numerical value for the stochastic intensity
    private float randomCoefficient = 0f;

    //The stochastic intensity of this module
    protected StochasticIntensity myStochasticIntensity = StochasticIntensity.NONE_STOCH;

    //The unique ID for this module (all the modules this module communicates
    // with should have the same ID)
    private int myID = 0;

    //The name of this module (should be the same in the nameserver)
    private String myName = "NoName";

    protected Map<Long, Malfunction> myMalfunctions;

    protected List<MalfunctionImpl> myScheduledMalfunctions;

    private boolean canFail = false;
    
    private boolean hasFailed = false;

    //What I think the current tick is.
    private int myTicks = 0;
    
    //in hours (can be a fraction of an hour)
    private float myTickInterval = 1f;
    
    private FailureDecider myFailureDecider;

    protected Logger myLogger;
    
    /**
     * Constructor to create a BioModule, should only be called by those
     * deriving from BioModule.
     * 
     * @param pID
     *            The unique ID for this module (all the modules this module
     *            communicates with should have the same ID)
     * @param pName
     *            The name of the module
     */
    protected BioModuleImpl() {
        this(0, "Unnamed");
    }

    /**
     * Constructor to create a BioModule, should only be called by those
     * deriving from BioModule.
     * 
     * @param pID
     *            The unique ID for this module (all the modules this module
     *            communicates with should have the same ID)
     * @param pName
     *            The name of the module
     */
    protected BioModuleImpl(int pID, String pName) {
        myLogger = Logger.getLogger(this.getClass()+"."+pName);
        myRandomGen = new Random();
        myMalfunctions = new Hashtable<Long, Malfunction>();
        myScheduledMalfunctions = new Vector<MalfunctionImpl>();
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
    }

    public int getMyTicks() {
        return myTicks;
    }

    private void checkForFailure() {
        if (myFailureDecider.hasFailed(myTicks)){
        	myLogger.info(getModuleName() + " has failed!");
        	hasFailed = true;
            startMalfunction(MalfunctionIntensity.SEVERE_MALF,MalfunctionLength.PERMANENT_MALF);
        }
    }

    private void checkForScheduledMalfunctions() {
    	for (MalfunctionImpl currentMalfunction : myScheduledMalfunctions) {
            if (currentMalfunction.getTickToMalfunction() == getMyTicks())
                startMalfunction(currentMalfunction);
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
                myMalfunctions.remove(new Long(currentMalfunction.getID()));
		}
    }

    /**
     * Clears all malfunctions regardless of temporal length (i.e., permanent
     * malfunctions removed)
     */
    public void clearAllMalfunctions() {
    	for (Malfunction currentMalfunction : myMalfunctions.values())
            myMalfunctions.remove(new Long(currentMalfunction.getID()));
    }

    /**
     * Tells the names of the malfunctions currently active with this module
     * 
     * @return An array of String containing the names of the malfunctions
     *         currently active with this module
     */
    public String[] getMalfunctionNames() {
        String[] malfunctionNames = new String[myMalfunctions.size()];
        int i = 0;
    	for (Malfunction currentMalfunction : myMalfunctions.values()){
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
     * @param malfunctionID
     *            the ID of the malfunction to repair
     */
    public void doSomeRepairWork(long malfunctionID) {
        Malfunction currentMalfunction = (myMalfunctions
                .get(new Long(malfunctionID)));
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
     * @param pIntensity
     *            the intensity of the malfunction
     * @param pLength
     *            the temporal length of the malfunction
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
     * @param pIntensity
     *            the intensity of the malfunction
     * @param pLength
     *            the temporal length of the malfunction
     * @return the malfunction started
     */
    public Malfunction startMalfunction(MalfunctionIntensity pIntensity,
            MalfunctionLength pLength) {
        String malfunctionName = getMalfunctionName(pIntensity, pLength);
        MalfunctionImpl newMalfunctionImpl = new MalfunctionImpl(
                malfunctionName, pIntensity, pLength, getTickLength());
        Malfunction newMalfunction = MalfunctionHelper.narrow(OrbUtils
                .poaToCorbaObj(newMalfunctionImpl));
        myMalfunctions.put((new Long(newMalfunction.getID())), newMalfunction);
        return newMalfunction;
    }

    /**
     * Starts a malfunction in this module.
     * 
     * @param pMalfunction
     *            the malfunction
     */
    private void startMalfunction(MalfunctionImpl pMalfunction) {
        Malfunction newMalfunction = MalfunctionHelper.narrow(OrbUtils
                .poaToCorbaObj(pMalfunction));
        myMalfunctions.put((new Long(newMalfunction.getID())), newMalfunction);
    }

    /**
     * Schedules a malfunction in this module.
     * 
     * @param pIntensity
     *            the intensity of the malfunction
     * @param pLength
     *            the temporal length of the malfunction
     * @return the malfunction started
     */
    public void scheduleMalfunction(MalfunctionIntensity pIntensity,
            MalfunctionLength pLength, int tickToOccur) {
        String malfunctionName = getMalfunctionName(pIntensity, pLength);
        MalfunctionImpl newMalfunctionImpl = new MalfunctionImpl(
                malfunctionName, pIntensity, pLength, getTickLength());
        newMalfunctionImpl.setTickToMalfunction(tickToOccur);
        myScheduledMalfunctions.add(newMalfunctionImpl);
    }

    /**
     * Fixes a temporary malfunction currently active with this module
     * instantly.
     * 
     * @param pID
     *            the ID of the malfunction to remove
     */
    public void fixMalfunction(long pID) {
        Malfunction theMalfunction = (myMalfunctions
                .get(new Long(pID)));
        if (theMalfunction.getLength() == MalfunctionLength.TEMPORARY_MALF)
            myMalfunctions.remove(new Long(pID));
    }

    /**
     * Clears a malfunction currently active with this module regardless of
     * temporal length (i.e., permanent malfunctions removed)
     * 
     * @param malfunctionID
     *            the ID of the malfunction to remove
     */
    public void clearMalfunction(long malfunctionID) {
        myMalfunctions.remove(new Long(malfunctionID));
    }

    /**
     * Tells if this module is malfunctioning (i.e. has > 0 malfunctions)
     * 
     * @return if this module is malfunctioning (i.e. has > 0 malfunctions),
     *         returns <code>true</code>, else <code>false</code>
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
        hasFailed = false;
        myMalfunctions.clear();
    	for (MalfunctionImpl currentMalfunction : myScheduledMalfunctions)
    		currentMalfunction.reset();
    }

    /**
     * Tells The ID of this module. Should be the same as every other module in
     * this BioSim instance
     * 
     * @return The ID of this module. Should be the same as every other module
     *         in this BioSim instance
     */
    public int getID() {
        return myID;
    }

    public void setEnableFailure(boolean pValue) {
        canFail = pValue;
    }

    public boolean isFailureEnabled() {
        return canFail;
    }
    
    public void maintain() {
        //does nothing right now
    }
    
    public void setLogLevel(LogLevel pLevel){
    	if (pLevel == LogLevel.OFF)
    		myLogger.setLevel(Level.OFF);
    	else if (pLevel == LogLevel.INFO)
    		myLogger.setLevel(Level.INFO);
    	else if (pLevel == LogLevel.DEBUG)
    		myLogger.setLevel(Level.DEBUG);
    	else if (pLevel == LogLevel.ERROR)
    		myLogger.setLevel(Level.ERROR);
    	else if (pLevel == LogLevel.WARN)
    		myLogger.setLevel(Level.WARN);
    	else if (pLevel == LogLevel.FATAL)
    		myLogger.setLevel(Level.FATAL);
    	else if (pLevel == LogLevel.ALL)
    		myLogger.setLevel(Level.ALL);
    }

    /**
     * Tells the Stochastic intentsity of this module (i.e., the indeterminism)
     * 
     * @return The Stochastic intentsity of this module (i.e., the
     *         indeterminism)
     */
    public StochasticIntensity getStochasticIntensity() {
        return myStochasticIntensity;
    }

    /**
     * Sets how stochastically intense this module should be.
     * 
     * @param pValue
     *            The intensity for this module. Options are: <br>
     *            &nbsp;&nbsp;&nbsp; <code>StochasticIntensity.HIGH_STOCH</code>
     *            for high intensity <br>
     *            &nbsp;&nbsp;&nbsp;
     *            <code>StochasticIntensity.MEDIUM_STOCH</code> for medium
     *            intensity <br>
     *            &nbsp;&nbsp;&nbsp; <code>StochasticIntensity.LOW_STOCH</code>
     *            for low intensity <br>
     *            &nbsp;&nbsp;&nbsp; <code>StochasticIntensity.NONE_STOCH</code>
     *            for no intensity (deterministic, default)
     */
    public void setStochasticIntensity(StochasticIntensity pValue) {
        myStochasticIntensity = pValue;
        if (pValue == StochasticIntensity.NONE_STOCH)
            randomCoefficient = 0f;
        else if (pValue == StochasticIntensity.LOW_STOCH)
            randomCoefficient = .02f;
        else if (pValue == StochasticIntensity.MEDIUM_STOCH)
            randomCoefficient = .04f;
        else if (pValue == StochasticIntensity.HIGH_STOCH)
            randomCoefficient = .08f;
    }

    /**
     * Randomizes a number passed through it.
     * 
     * @param pValue
     *            Filters using a gaussian function.
     * @return the randomized result
     */
    public float randomFilter(float pValue) {
        if (randomCoefficient <= 0)
            return pValue;
        double deviation = randomCoefficient * pValue;
        double result = MathUtils.gaussian(pValue, deviation);
        if (result < 0)
            return 0;
		return (float)result;
    }

    /**
     * Randomizes a number passed through it.
     * 
     * @param pValue
     *            Filters using a gaussian function.
     * @return the randomized result
     */
    public double randomFilter(double pValue) {
        if (randomCoefficient <= 0)
            return pValue;
        double deviation = randomCoefficient * pValue;
        double result = MathUtils.gaussian(pValue, deviation);
        if (result < 0)
            return 0;
		return result;
    }

    /**
     * Randomizes a number passed through it.
     * 
     * @param pValue
     *            Filters using a gaussian function.
     * @return the randomized result
     */
    public boolean randomFilter(boolean pValue) {
        if (randomCoefficient <= 0)
            return pValue;
        return (MathUtils.gaussian(1, randomCoefficient) > 1);
    }

    /**
     * Randomizes a number passed through it.
     * 
     * @param pValue
     *            Filters using a gaussian function.
     * @return the randomized result
     */
    public int randomFilter(int pValue) {
        if (randomCoefficient <= 0)
            return pValue;
        double deviation = randomCoefficient * pValue;
        double result = MathUtils.gaussian(pValue, deviation);
        if (result < 0)
            return 0;
		return (int)result;
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
        myName =  pName;
    }
    
    public float getTickLength(){
    	return myTickInterval;
    }
    
    public void setTickLength(float pInterval){
    	myTickInterval = pInterval;
    }

	public void setFailureDecider(FailureDecider decider) {
		this.myFailureDecider = decider;
	}
}
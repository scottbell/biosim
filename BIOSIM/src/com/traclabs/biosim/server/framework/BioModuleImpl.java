package biosim.server.framework;

import java.util.*;
import biosim.idl.framework.*;
import biosim.idl.simulation.framework.*;
import biosim.server.util.*;
import biosim.server.util.log.*;
import biosim.idl.util.log.*;
/**
 * The BioModule Implementation.  Every Module should derive from this as to allow ticking and logging.
 *
 * @author    Scott Bell
 */

public abstract class BioModuleImpl extends BioModulePOA{
	//The random number generator used for gaussian function (stochastic stuff)
	private Random myRandomGen;
	//Whether the log for this module is initialized or not
	protected boolean logInitialized = false;
	//Whether this module is logging or not
	protected boolean moduleLogging = false;
	//The root log node for this module.  Below this are name/value pairs of data
	protected LogNodeImpl myLog;
	//The logger that this module sends it's log node to
	private Logger myLogger;
	//Whether this module has collected a reference to logger server
	private boolean collectedLogger = false;
	//The numerical value for the stochastic intensity
	private float randomCoefficient = 0f;
	//The stochastic intensity of this module
	protected StochasticIntensity myStochasticIntensity = StochasticIntensity.NONE_STOCH;
	//The unique ID for this module (all the modules this module communicates with should have the same ID)
	private int myID = 0;
	//The Malfunctions in a Map (key is a Long representing the Malfunction ID, value is the Malfunction Object)
	protected Map myMalfunctions;
	
	/**
	* Constructor to create a BioModule, should only be called by those deriving from BioModule.
	* @param pID The unique ID for this module (all the modules this module communicates with should have the same ID)
	*/
	protected BioModuleImpl(int pID){
		myRandomGen = new Random();
		myMalfunctions = new Hashtable();
		myLog = new LogNodeImpl(getModuleName());
		myID = pID;
	}
	
	/**
	* Called at every tick of the simulation.  Does nothing if not overriden.
	*/
	public void tick(){
		if (moduleLogging)
			log();
	}
	
	/**
	* Fixes all malfunctions.  Permanent malfunctions are unfixable.
	*/
	public void fixAllMalfunctions(){
		for (Iterator iter = myMalfunctions.values().iterator(); iter.hasNext(); ){
			Malfunction currentMalfunction = (Malfunction)(iter.next());
			if (currentMalfunction.getLength() == MalfunctionLength.TEMPORARY_MALF)	
				myMalfunctions.remove(new Long(currentMalfunction.getID()));
		}
	}
	
	/**
	* Clears all malfunctions regardless of temporal length (i.e., permanent malfunctions removed)
	*/
	public void clearAllMalfunctions(){
		for (Iterator iter = myMalfunctions.values().iterator(); iter.hasNext(); ){
			Malfunction currentMalfunction = (Malfunction)(iter.next());
			myMalfunctions.remove(new Long(currentMalfunction.getID()));
		}
	}
	
	/**
	* Tells the names of the malfunctions currently active with this module
	* @return An array of String containing the names of the malfunctions currently active with this module
	*/
	public String[] getMalfunctionNames(){
		String[] malfunctionNames = new String[myMalfunctions.size()];
		int i = 0;
		for (Iterator iter = myMalfunctions.values().iterator(); iter.hasNext(); i++){
			Malfunction currentMalfunction = (Malfunction)(iter.next());
			malfunctionNames[i] = currentMalfunction.getName();
		}
		return malfunctionNames;
	}
	
	/**
	* Tells the malfunctions currently active with this module
	* @return An array of malfunctions currently active with this module
	*/
	public Malfunction[] getMalfunctions(){
		Malfunction[] arrayMalfunctions = new Malfunction[myMalfunctions.size()];
		return (Malfunction[])(myMalfunctions.values().toArray(arrayMalfunctions));
	}
	
	/**
	* Fixes a malfunction currently active with this module.  The malfunction must be "repaired" a certain number of times depending
	* on the intensity/severity of the malfunction.
	* @param malfunctionID the ID of the malfunction to repair
	*/
	public void repair(long malfunctionID){
		Malfunction currentMalfunction = (Malfunction)(myMalfunctions.get(new Long(malfunctionID)));
		if (currentMalfunction == null)
			return;
		currentMalfunction.repair();
		if (currentMalfunction.isRepaired())
			fixMalfunction(currentMalfunction.getID());
	}

	/**
	 * Returns a nice description of the malfunction.  The default implementation (provided here) should
	 * be overriden in the specific modules (i.e., temporary low mafunction means a small leak in the Store modules)
	 * @param pIntensity the intensity of the malfunction
	 * @param pLength the temporal length of the malfunction
	 * @return the description/name of the malfunction specified
	 */
	protected String getMalfunctionName(MalfunctionIntensity pIntensity, MalfunctionLength pLength){
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
	 * @param pIntensity the intensity of the malfunction
	 * @param pLength the temporal length of the malfunction
	 * @return the malfunction started
	 */
	public Malfunction startMalfunction(MalfunctionIntensity pIntensity, MalfunctionLength pLength){
		String malfunctionName = getMalfunctionName(pIntensity, pLength);
		MalfunctionImpl newMalfunctionImpl = new MalfunctionImpl(malfunctionName,pIntensity,pLength);
		Malfunction newMalfunction = MalfunctionHelper.narrow(OrbUtils.poaToCorbaObj(newMalfunctionImpl));
		myMalfunctions.put((new Long(newMalfunction.getID())), newMalfunction);
		return newMalfunction;
	}
	
	/**
	* Fixes a temporary malfunction currently active with this module instantly.
	* @param malfunctionID the ID of the malfunction to remove
	*/
	public void fixMalfunction(long pID){
		Malfunction theMalfunction = (Malfunction)(myMalfunctions.get(new Long(pID)));
		if (theMalfunction.getLength() == MalfunctionLength.TEMPORARY_MALF)	
			myMalfunctions.remove(new Long(pID));
	}
	
	/**
	* Clears a malfunction currently active with this module regardless of temporal length (i.e., permanent malfunctions removed)
	* @param malfunctionID the ID of the malfunction to remove
	*/
	public void clearMalfunction(long malfunctionID){
		myMalfunctions.remove(new Long(malfunctionID));
	}
	
	/**
	* Tells if this module is malfunctioning (i.e. has > 0 malfunctions)
	* @return if this module is malfunctioning (i.e. has > 0 malfunctions), returns <code>true</code>, else <code>false</code>
	*/
	public boolean isMalfunctioning(){
		return (myMalfunctions.size() > 0);
	}
	
	/**
	* Resets this module (should be called by extended classes as it clears malfunctions)
	*/
	public void reset(){
		myMalfunctions.clear();
	}

	/**
	* Logs this module to the Logger server
	*/
	private void log(){
	}
	
	/**
	* Sets the logging for this module.
	* @param pLogging <code>true</code> if this module should log, <code>false</code> if not
	*/
	public void setLogging(boolean pLogging){
		moduleLogging = pLogging;
	}
	
	/**
	* Tells if this module is logging
	* @return <code>true</code> if this module is logging, <code>false</code> if not
	*/
	public boolean isLogging(){
		return moduleLogging;
	}
	
	/**
	* Tells The ID of this module.  Should be the same as every other module in this BioSim instance
	* @return The ID of this module.  Should be the same as every other module in this BioSim instance
	*/
	public int getID(){
		return myID;
	}
	
	/**
	* Maintains this module.  Must be invoked from time to time to prevent this risk of spoontaneous malfunctions.
	* The longer into the sim (i.e., the more the module has been ticked), the greater this risk is.
	* NOT IMPLEMENTED YET
	*/
	public void maitenance(){
	}
	
	/**
	* Tells the Stochastic intentsity of this module (i.e., the indeterminism)
	* @return The Stochastic intentsity of this module (i.e., the indeterminism)
	*/
	public StochasticIntensity getStochasticIntensity(){
		return myStochasticIntensity;
	}
	
	/**
	* Sets how stochastically intense this module should be.
	* @param pValue The intensity for this module.  Options are:<br>
	* &nbsp;&nbsp;&nbsp;<code>StochasticIntensity.HIGH_STOCH</code> for high intensity<br>
	* &nbsp;&nbsp;&nbsp;<code>StochasticIntensity.MEDIUM_STOCH</code> for medium intensity<br>
	* &nbsp;&nbsp;&nbsp;<code>StochasticIntensity.LOW_STOCH</code> for low intensity<br>
	* &nbsp;&nbsp;&nbsp;<code>StochasticIntensity.NONE_STOCH</code> for no intensity (deterministic, default)
	*/
	public void setStochasticIntensity(StochasticIntensity pValue){
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
	* @param pValue Filters using a gaussian function.
	* @return the randomized result
	*/
	public float randomFilter(float pValue){
		if (randomCoefficient <= 0)
			return pValue;
		double deviation = randomCoefficient * pValue;
		double result = gaussian(pValue, deviation);
		if (result < 0)
			return 0;
		else
			return (new Double(result)).floatValue();
	}
	
	/**
	* Randomizes a number passed through it.
	* @param pValue Filters using a gaussian function.
	* @return the randomized result
	*/
	public double randomFilter(double pValue){
		if (randomCoefficient <= 0)
			return pValue;
		double deviation = randomCoefficient * pValue;
		double result = gaussian(pValue, deviation);
		if (result < 0)
			return 0;
		else
			return result;
	}
	
	/**
	* Randomizes a number passed through it.
	* @param pValue Filters using a gaussian function.
	* @return the randomized result
	*/
	public boolean randomFilter(boolean pValue){
		if (randomCoefficient <= 0)
			return pValue;
		double deviation = randomCoefficient * 1;
		return (gaussian(1, deviation) > 1);
	}
	
	/**
	* Randomizes a number passed through it.
	* @param pValue Filters using a gaussian function.
	* @return the randomized result
	*/
	public int randomFilter(int pValue){
		if (randomCoefficient <= 0)
			return pValue;
		double deviation = randomCoefficient * pValue;
		double result = gaussian(pValue, deviation);
		if (result < 0)
			return 0;
		else
			return (new Double(result)).intValue();
	}
	
	/**
	* Sends a log node to the Logger server
	* @param logToProcess the log node to process (i.e., send to the Logger server)
	*/
	protected void sendLog(LogNodeImpl logToProcess){
		if (!collectedLogger){
			try{
				myLogger = LoggerHelper.narrow(OrbUtils.getNCRef().resolve_str("Logger"));
				collectedLogger = true;
			}
			catch (org.omg.CORBA.UserException e){
				e.printStackTrace(System.out);
			}
		}
		myLogger.processLog(LogNodeHelper.narrow(OrbUtils.poaToCorbaObj(logToProcess)));
	}
	
	/**
	* A basic Gaussian function
	* @param mean where the gaussian is "centered".  e.g., a value of 3 would yield numbers around 3
	* @param deviation how far the gaussian deviates from the mean
	* @return the randomized value
	*/
	private double gaussian(double mean,double deviation){
		double t = 0.0;
		double x,v1,v2,r;
		if (t == 0) {
			do {
				v1 = 2.0 * Math.random() - 1.0;
				v2 = 2.0 * Math.random() - 1.0;
				r = v1 * v1 + v2 * v2;
			} while (r>=1.0);
			r = Math.sqrt((-2.0*Math.log(r))/r);
			t = v2*r;
			return(mean+v1*r*deviation);
		}
		else {
			x = t;
			t = 0.0;
			return(mean+x*deviation);
		}
	}
	
	/**
	* Grabs as much resources as it can (i.e., the maxFlowRate) from a store.
	* @param pStores The stores to grab the resources from
	* @param pMaxFlowRates The flow rates from this module to the stores
	* @return The total amount of resource grabbed from the stores
	*/
	public static float getMaxResourceFromStore(Store[] pStores, float[] pMaxFlowRates){
		float gatheredResource = 0f;
		for (int i = 0; i < pStores.length; i++){
			gatheredResource += pStores[i].take(pMaxFlowRates[i]);
		}
		return gatheredResource;
	}
	
	/**
	* Attempts to grab a specified amount from a collection of stores
	* @param pStores The stores to grab the resources from
	* @param pMaxFlowRates The flow rates from this module to the stores
	* @param amountNeeded The amount to gather from the stores
	* @return The total amount of resource grabbed from the stores (equal to the amount needed if sucessful)
	*/
	public static float getResourceFromStore(Store[] pStores, float[] pMaxFlowRates, float amountNeeded){
		float gatheredResource = 0f;
		for (int i = 0; (i < pStores.length) && (gatheredResource < amountNeeded); i++){
			float resourceToGather = Math.min(amountNeeded, pMaxFlowRates[i]); 
			gatheredResource += pStores[i].take(resourceToGather);
		}
		return gatheredResource;
	}
	
	/**
	* Attempts to push a specified amount to a collection of stores
	* @param pStores The stores to push the resources to
	* @param pMaxFlowRates The flow rates from this module to the stores
	* @param amountToPush The amount to push to the stores
	* @return The total amount of resource pushed to the stores (equal to the amount to push if sucessful)
	*/
	public static float pushResourceToStore(Store[] pStores, float[] pMaxFlowRates, float amountToPush){
		float totalResourceDistributed = amountToPush;
		for (int i = 0; (i < pStores.length) && (totalResourceDistributed > 0); i++){
			float resourceToDistribute = Math.min(totalResourceDistributed, pMaxFlowRates[i]);
			totalResourceDistributed -= pStores[i].add(resourceToDistribute);
		}
		return (amountToPush - totalResourceDistributed);
	}

	/**
	* Returns the name of the module, "Unamed" if not overriden
	* @return the name of this module
	*/
	public String getModuleName(){
		return "Unamed"+myID;
	}
}


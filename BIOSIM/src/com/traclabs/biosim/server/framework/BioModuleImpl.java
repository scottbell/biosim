package biosim.server.framework;

import java.util.*;
import biosim.idl.framework.*;
import biosim.server.util.*;
import biosim.server.util.log.*;
import biosim.idl.util.log.*;
/**
 * The BioModule Implementation.  Every Module should derive from this as to allow ticking and logging.
 *
 * @author    Scott Bell
 */

public abstract class BioModuleImpl extends BioModulePOA{
	private Random myRandomGen;
	protected boolean logInitialized = false;
	protected boolean moduleLogging = false;
	protected LogNodeImpl myLog;
	private Logger myLogger;
	private boolean collectedLogger = false;
	private float randomCoefficient = 0f;
	private int myID = 0;
	private static final int RANDOM_PRECISION = 100;

	public BioModuleImpl(int pID){
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
	* Logs this module to the Logger
	*/
	private void log(){
	}

	public void setLogging(boolean pLogging){
		moduleLogging = pLogging;
	}

	public boolean isLogging(){
		return moduleLogging;
	}
	
	public int getID(){
		return myID;
	}
	
	public void setRandomCoefficient(float pValue){
		randomCoefficient = pValue;
	}
	
	protected float randomFilter(float pValue){
		myRandomGen = new Random();
		if (randomCoefficient <= 0)
			return pValue;
		int biggerCoef = (new Float(randomCoefficient * RANDOM_PRECISION)).intValue();
		float randomBiggerCoef = (new Integer(myRandomGen.nextInt(biggerCoef))).floatValue();
		float coefficient = randomBiggerCoef / RANDOM_PRECISION;
		float addSubtractValue = coefficient * pValue;
		int addSubtractDecider = myRandomGen.nextInt(2);
		float newValue;
		if (addSubtractDecider == 0)
			return (pValue += addSubtractValue);
		else
			return (pValue -= addSubtractValue);
	}
	
	protected boolean randomFilter(boolean pValue){
		myRandomGen = new Random();
		if (randomCoefficient <= 0)
			return pValue;
		int biggerCoef = (new Float(randomCoefficient * RANDOM_PRECISION)).intValue();
		int decider = myRandomGen.nextInt(100);
		if (decider < biggerCoef)
			return !pValue;
		else
			return pValue;
	}
	
	protected int randomFilter(int pValue){
		myRandomGen = new Random();
		if (randomCoefficient <= 0)
			return pValue;
		int biggerCoef = (new Float(randomCoefficient * RANDOM_PRECISION)).intValue();
		float randomBiggerCoef = (new Integer(myRandomGen.nextInt(biggerCoef))).floatValue();
		float coefficient = randomBiggerCoef / RANDOM_PRECISION;
		float addSubtractValue = coefficient * pValue;
		int addSubtractDecider = myRandomGen.nextInt(2);
		int newValue;
		if (addSubtractDecider == 0)
			return (pValue += addSubtractValue);
		else
			return (pValue -= addSubtractValue);
	}

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
	* Returns the name of the module, "Unamed" if not overriden
	* @return the name of this module
	*/
	public String getModuleName(){
		return "Unamed"+myID;
	}
}


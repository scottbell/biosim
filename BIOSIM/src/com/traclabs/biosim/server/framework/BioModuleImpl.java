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
	protected StochasticIntensity myStochasticIntensity = StochasticIntensity.NONE;
	private int myID = 0;
	private static final int RANDOM_PRECISION = 1000;

	public BioModuleImpl(int pID){
		myRandomGen = new Random();
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
	
	public StochasticIntensity getStochasticIntensity(){
		return myStochasticIntensity;
	}
	
	public void setStochasticIntensity(StochasticIntensity pValue){
		myStochasticIntensity = pValue;
		if (pValue == StochasticIntensity.NONE)
			randomCoefficient = 0f;
		else if (pValue == StochasticIntensity.LOW)
			randomCoefficient = .03f;
		else if (pValue == StochasticIntensity.MEDIUM)
			randomCoefficient = .06f;
		else if (pValue == StochasticIntensity.HIGH)
			randomCoefficient = .09f;
	}
	
	public float randomFilter(float pValue){
		if (randomCoefficient <= 0)
			return pValue;
		int biggerCoef = (new Float(randomCoefficient * RANDOM_PRECISION)).intValue();
		float randomBiggerCoef = (new Integer(myRandomGen.nextInt(biggerCoef))).floatValue();
		float coefficient = randomBiggerCoef / RANDOM_PRECISION;
		float addSubtractValue = coefficient * pValue;
		int addSubtractDecider = myRandomGen.nextInt(2);
		if (addSubtractDecider == 0)
			return (pValue += addSubtractValue);
		else
			return (pValue -= addSubtractValue);
	}
	
	public double randomFilter(double pValue){
		if (randomCoefficient <= 0)
			return pValue;
		int biggerCoef = (new Float(randomCoefficient * RANDOM_PRECISION)).intValue();
		float randomBiggerCoef = (new Integer(myRandomGen.nextInt(biggerCoef))).floatValue();
		float coefficient = randomBiggerCoef / RANDOM_PRECISION;
		double addSubtractValue = coefficient * pValue;
		int addSubtractDecider = myRandomGen.nextInt(2);
		if (addSubtractDecider == 0)
			return (pValue += addSubtractValue);
		else
			return (pValue -= addSubtractValue);
	}
	
	public boolean randomFilter(boolean pValue){
		if (randomCoefficient <= 0)
			return pValue;
		int biggerCoef = (new Float(randomCoefficient * RANDOM_PRECISION)).intValue();
		int decider = myRandomGen.nextInt(100);
		if (decider < biggerCoef)
			return !pValue;
		else
			return pValue;
	}
	
	public int randomFilter(int pValue){
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


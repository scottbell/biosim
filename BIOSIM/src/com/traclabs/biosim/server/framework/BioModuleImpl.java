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
	protected StochasticIntensity myStochasticIntensity = StochasticIntensity.NONE_STOCH;
	private int myID = 0;
	private static final int RANDOM_PRECISION = 1000;
	protected Map myMalfunctions;

	public BioModuleImpl(int pID){
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
	
	public void fixAllMalfunctions(){
		for (Iterator iter = myMalfunctions.values().iterator(); iter.hasNext(); ){
			Malfunction currentMalfunction = (Malfunction)(iter.next());
			if (currentMalfunction.getLength() == MalfunctionLength.TEMPORARY_MALF)	
				myMalfunctions.remove(new Long(currentMalfunction.getID()));
		}
	}
	
	public String[] getMalfunctionNames(){
		String[] malfunctionNames = new String[myMalfunctions.size()];
		int i = 0;
		for (Iterator iter = myMalfunctions.values().iterator(); iter.hasNext(); i++){
			Malfunction currentMalfunction = (Malfunction)(iter.next());
			malfunctionNames[i] = currentMalfunction.getName();
		}
		return malfunctionNames;
	}
	
	public Malfunction[] getMalfunctions(){
		Malfunction[] arrayMalfunctions = new Malfunction[myMalfunctions.size()];
		return (Malfunction[])(myMalfunctions.values().toArray(arrayMalfunctions));
	}

	/**
	 * Override this to get custom malfunction names.
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

	public Malfunction startMalfunction(MalfunctionIntensity pIntensity, MalfunctionLength pLength){
		String malfunctionName = getMalfunctionName(pIntensity, pLength);
		MalfunctionImpl newMalfunctionImpl = new MalfunctionImpl(malfunctionName,pIntensity,pLength);
		Malfunction newMalfunction = MalfunctionHelper.narrow(OrbUtils.poaToCorbaObj(newMalfunctionImpl));
		myMalfunctions.put((new Long(newMalfunction.getID())), newMalfunction);
		return newMalfunction;
	}

	public void fixMalfunction(long pID){
		Malfunction theMalfunction = (Malfunction)(myMalfunctions.get(new Long(pID)));
		if (theMalfunction.getLength() == MalfunctionLength.TEMPORARY_MALF)	
			myMalfunctions.remove(new Long(pID));
	}

	public boolean isMalfunctioning(){
		return (myMalfunctions.size() > 0);
	}
	
	public void reset(){
		myMalfunctions.clear();
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
	
	public void maitenance(){
	}

	public StochasticIntensity getStochasticIntensity(){
		return myStochasticIntensity;
	}

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

	public boolean randomFilter(boolean pValue){
		if (randomCoefficient <= 0)
			return pValue;
		double deviation = randomCoefficient * 1;
		return (gaussian(1, deviation) > 1);
	}

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
	
	/* ------------------------------------------------ *
	* gaussian -- generates a gaussian random variable *
	*             with mean a and standard deviation d *
	* ------------------------------------------------ */
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
	* Returns the name of the module, "Unamed" if not overriden
	* @return the name of this module
	*/
	public String getModuleName(){
		return "Unamed"+myID;
	}
}


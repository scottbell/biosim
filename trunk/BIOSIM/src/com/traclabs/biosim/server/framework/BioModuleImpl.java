package biosim.server.framework;

import biosim.idl.framework.*;
import biosim.server.util.*;
import biosim.idl.util.*;
/**
 * The BioModule Implementation.  Every Module should derive from this as to allow ticking and logging.
 *
 * @author    Scott Bell
 */

public abstract class BioModuleImpl extends BioModulePOA{
	protected boolean logInitialized = false;
	protected boolean moduleLogging = false;
	protected LogNodeImpl myLog;
	private Logger myLogger;
	private boolean collectedLogger = false;

	public BioModuleImpl(){
		myLog = new LogNodeImpl(getModuleName());
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
		return "Unamed";
	}
}


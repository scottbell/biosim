package biosim.server.util.log;

import biosim.server.util.*;
import biosim.idl.util.log.*;
import biosim.idl.framework.*;
import java.util.*;
/**
 * The Logger takes Logs from the modules and outputs them using choosen handlers
 * @author    Scott Bell
 */

public class LoggerImpl extends LoggerPOA  {
	//The handlers associated with this Logger (i.e., the outputs)
	private List myLogHandlers;
	//List of the types supported by this Logger (i.e. handler types_
	private List logTypes;
	//Whether the Logger has collected a reference 
	private boolean hasCollectedReferences = false;
	//Used to collect current "tick"
	private BioDriver myDriver;
	//Whether the Logger should bother processing logs it recieves
	private boolean processingLogs = true;
	//The current node on which to tack on all the rest of the log nodes received
	private LogNodeImpl currentTickLogNode;
	//The root node onto which all tick nodes are tacked onto
	private LogNodeImpl rootLogNode;
	//The current tick from the Logger's perspective
	private int currentTick = -1;
	//The ID of this Logger (should be the same as all the rest of the modules sending logs and the BioDriver)
	private int myID = 0;
	
	/**
	* Creates a Logger Server with an ID (should be the same as all the rest of the modules sending logs and the BioDriver)<br>
	* Also initializes logtypes and adds and XML handler.
	* @param pID the ID of this logger (should be the same as all the rest of the modules sending logs and the BioDriver)
	*/
	public LoggerImpl(int pID){
		myID = pID;
		rootLogNode = new LogNodeImpl("");
		myLogHandlers = new Vector();
		logTypes = new Vector();
		//addLogHandlerType(LogHandlerType.SCREEN);
		addLogHandlerType(LogHandlerType.XML);
	}
	
	/**
	* Tells the logger handler types (i.e., what the logger is outputting to, XML, Screen, text file, etc)
	* @return pID an array of the logger handler types (outputs)
	*/
	public LogHandlerType[] getLogHandlerTypes(){
		return (LogHandlerType[])(logTypes.toArray());
	}

	/**
	* Collects references to servers needed for putting/getting resources.
	*/
	private void collectReferences(){
		try{
			if (!hasCollectedReferences){
				myDriver = BioDriverHelper.narrow(OrbUtils.getNamingContext(getID()).resolve_str("BioDriver"));
				hasCollectedReferences = true;
			}
		}
		catch (org.omg.CORBA.UserException e){
			e.printStackTrace(System.out);
		}
	}
	
	/**
	* Tells the name of the module
	* @return the name of the module
	*/
	public String getName(){
		return "Logger";
	}
	
	/**
	* Tells The ID of this module.  Should be the same as every other module in this BioSim instance
	* @return The ID of this module.  Should be the same as every other module in this BioSim instance
	*/
	public int getID(){
		return myID;
	}
	
	/**
	* Switch to turn off the Logger's processing of logs (useful to stop logging globally)
	* @param pAllowLogging set <code>true</code> to have Logger process incoming logs (default) or
	* <code>false</code> to disable log processing.
	*/
	public void setProcessingLogs(boolean pAllowLogging){
		processingLogs = pAllowLogging;
		if (!processingLogs)
			endLog();
	}
	
	/**
	* Tells the handler to flush its output
	*/
	public void endLog(){
		for (Iterator iter = myLogHandlers.iterator(); iter.hasNext();){
			LogHandler currentLogHandler = (LogHandler)(iter.next());
			currentLogHandler.endLog();
		}
	}
	
	/**
	* Tells if the Logger is processing logs received.  If it isn't, Logger does nothing with LogNodes received
	* @return if the Logger is processing logs received
	*/
	public boolean isProcessingLogs(){
		return processingLogs;
	}
	
	/**
	* Adds another output for the Logger to write to.
	* @param pLogType the log type to add.  Options are:<br>
	* &nbsp;&nbsp;&nbsp;<code>LogHandlerType.SCREEN</code> for screen output<br>
	* &nbsp;&nbsp;&nbsp;<code>LogHandlerType.DB</code> for database output (not impelemented yet)<br>
	* &nbsp;&nbsp;&nbsp;<code>LogHandlerType.FLAT</code> for flat file output (not impelemented yet)<br>
	* &nbsp;&nbsp;&nbsp;<code>LogHandlerType.XML</code> for XML output (default)
	*/
	public void addLogHandlerType(LogHandlerType pLogType){
		if (logTypes.contains(pLogType))
			return;
		logTypes.add(pLogType);
		if (pLogType == LogHandlerType.SCREEN)
			myLogHandlers.add(new ScreenLogHandler());
		if (pLogType == LogHandlerType.DB)
			myLogHandlers.add(new ScreenLogHandler());
		if (pLogType == LogHandlerType.FLAT)
			myLogHandlers.add(new ScreenLogHandler());
		if (pLogType == LogHandlerType.XML)
			myLogHandlers.add(new XMLLogHandler());
	}
	
	/**
	* Other modules use this method to log their data.
	* @param logToAdd the LogNode to add to the log and (possibly) outuput
	*/
	public void processLog(LogNode logToAdd){
		if (!processingLogs)
			return;
		collectReferences();
		//One Tick has passed
		if ((currentTick != myDriver.getTicks()) && (currentTickLogNode != null)){
			for (Iterator iter = myLogHandlers.iterator(); iter.hasNext();){
				LogHandler currentLogHandler = (LogHandler)(iter.next());
				currentLogHandler.writeLog(LogNodeHelper.narrow(OrbUtils.poaToCorbaObj(currentTickLogNode)));
				//System.out.println("wrote log!");
			}
		}
		if (currentTick != myDriver.getTicks()){
			currentTick = myDriver.getTicks();
			currentTickLogNode = rootLogNode.addChildImpl("tick "+currentTick);
		}
		currentTickLogNode.addChild(logToAdd);
	}
}

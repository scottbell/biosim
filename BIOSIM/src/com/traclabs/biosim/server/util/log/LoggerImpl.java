package biosim.server.util;

import biosim.idl.util.*;
import biosim.idl.environment.*;
import java.util.*;
/**
 * The Logger takes Logs and outputs them using the choosen handler
 * @author    Scott Bell
 */

public class LoggerImpl extends LoggerPOA  {
	private Vector myLogHandlers;
	private Vector logTypes;
	private boolean hasCollectedReferences = false;
	private SimEnvironment myEnvironment;
	private boolean processingLogs = true;
	private LogNodeImpl currentTickLogNode;
	private LogNodeImpl rootLogNode;
	private int currentTick = -1;
	
	public LoggerImpl(){
		rootLogNode = new LogNodeImpl("");
		myLogHandlers = new Vector();
		logTypes = new Vector();
		//addLogHandlerType(LogHandlerType.SCREEN);
		addLogHandlerType(LogHandlerType.XML);
	}
	
	public LogHandlerType[] getLogHandlerTypes(){
		return (LogHandlerType[])(logTypes.toArray());
	}
	
	/**
	* Collects references to servers needed for putting/getting resources.
	*/
	private void collectReferences(){
		try{
			if (!hasCollectedReferences){
				myEnvironment = SimEnvironmentHelper.narrow(OrbUtils.getNCRef().resolve_str("SimEnvironment"));
				hasCollectedReferences = true;
			}
		}
		catch (org.omg.CORBA.UserException e){
			e.printStackTrace(System.out);
		}
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
	
	private void endLog(){
		for (Enumeration e = myLogHandlers.elements(); e.hasMoreElements();){
				LogHandler currentLogHandler = (LogHandler)(e.nextElement());
				currentLogHandler.endLog();
		}
	}
	
	public boolean isProcessingLogs(){
		return processingLogs;
	}
	
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
	
	public void processLog(LogNode logToAdd){
		if (!processingLogs)
			return;
		collectReferences();
		//One Tick has passed
		if ((currentTick != myEnvironment.getTicks()) && (currentTickLogNode != null)){
			for (Enumeration e = myLogHandlers.elements(); e.hasMoreElements();){
				LogHandler currentLogHandler = (LogHandler)(e.nextElement());
				currentLogHandler.writeLog(LogNodeHelper.narrow(OrbUtils.poaToCorbaObj(currentTickLogNode)));
			}
		}
		if (currentTick != myEnvironment.getTicks()){
			currentTick = myEnvironment.getTicks();
			currentTickLogNode = rootLogNode.addChildImpl("tick "+currentTick);
		}
		currentTickLogNode.addChild(logToAdd);
	}
}

package biosim.server.util;

import biosim.idl.util.*;
import biosim.idl.environment.*;
import java.util.*;
/**
 * The Logger takes Logs and outputs them using the choosen handler
 * @author    Scott Bell
 */

public class LoggerImpl extends LoggerPOA  {
	private LogHandler myLogHandler;
	private LogHandlerType logType;
	private boolean hasCollectedReferences = false;
	private SimEnvironment myEnvironment;
	
	public LoggerImpl(){
		//use default handler (System.out)
		setLogHandlerType(LogHandlerType.screen);
	}
	
	public LoggerImpl(LogHandlerType pLogType){
		setLogHandlerType(pLogType);
	}
	
	public LogHandlerType getLogHandlerType(){
		return logType;
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
	
	public void setLogHandlerType(LogHandlerType pLogType){
		logType = pLogType;
		if (logType == LogHandlerType.screen)
			myLogHandler = new ScreenLogHandler();
		if (logType == LogHandlerType.db)
			myLogHandler = new ScreenLogHandler();
		if (logType == LogHandlerType.flat)
			myLogHandler = new ScreenLogHandler();
		if (logType == LogHandlerType.xml)
			myLogHandler = new ScreenLogHandler();
	}
	
	public void processLog(Log logToProcess){
		collectReferences();
		LogNode headNode = logToProcess.getHead();
		LogNode tickLabel = headNode.addChild("tick");
		tickLabel.addChild(""+myEnvironment.getTicks());
		myLogHandler.writeLog(logToProcess);
	}
}

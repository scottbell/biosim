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
	private boolean processingLogs = true;
	
	public LoggerImpl(){
		//use default handler (System.out)
		setLogHandlerType(LogHandlerType.XML);
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
	
	/**
	* Switch to turn off the Logger's processing of logs (useful to stop logging globally)
	* @param pAllowLogging set <code>true</code> to have Logger process incoming logs (default) or
	* <code>false</code> to disable log processing.
	*/
	public void setProcessingLogs(boolean pAllowLogging){
		processingLogs = pAllowLogging;
	}
	
	public boolean isProcessingLogs(){
		return processingLogs;
	}
	
	public void setLogHandlerType(LogHandlerType pLogType){
		logType = pLogType;
		if (logType == LogHandlerType.SCREEN)
			myLogHandler = new ScreenLogHandler();
		if (logType == LogHandlerType.DB)
			myLogHandler = new ScreenLogHandler();
		if (logType == LogHandlerType.FLAT)
			myLogHandler = new ScreenLogHandler();
		if (logType == LogHandlerType.XML)
			myLogHandler = new XMLLogHandler();
	}
	
	public void processLog(Log logToProcess){
		if (!processingLogs)
			return;
		collectReferences();
		LogNode headNode = logToProcess.getHead();
		LogNode tickLabel = headNode.getChildShallow("tick");
		(tickLabel.getChildren())[0].setValue(""+myEnvironment.getTicks());
		myLogHandler.writeLog(logToProcess);
	}
}

package biosim.server.util.log;

import biosim.idl.util.log.*;
/**
 * The LogHandler takes Logs and outputs them
 * @author    Scott Bell
 */

public interface LogHandler {
	/**
	* Outputs a LogNode
	* @param logToWrite takes a LogNode and outputs it
	*/
	public void writeLog(LogNode logToWrite);
	/**
	* Called when log has ended.  Time to flush, close files, etc.
	*/
	public void endLog();
	
	public LogHandlerType getType();
}

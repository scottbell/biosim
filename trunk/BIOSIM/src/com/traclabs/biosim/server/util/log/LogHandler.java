package biosim.server.util.log;

import biosim.idl.util.log.*;
/**
 * The LogHandler takes Logs and outputs them
 * @author    Scott Bell
 */

public interface LogHandler {
	public void writeLog(LogNode logToWrite);
	public void endLog();
}

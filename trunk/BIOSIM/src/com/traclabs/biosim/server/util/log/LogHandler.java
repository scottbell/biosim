package biosim.server.util;

import biosim.idl.util.*;
/**
 * The LogHandler takes Logs and outputs them
 * @author    Scott Bell
 */

public interface LogHandler {
	public void writeLog(LogNode logToWrite);
	public void endLog();
}

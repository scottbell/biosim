package biosim.server.util;

import biosim.idl.util.*;
/**
 * The LogHandler takes Logs and outputs them
 * @author    Scott Bell
 */

public abstract class LogHandler {
	public abstract void writeLog(Log logToWrite);
}

package biosim.server.util;

import biosim.idl.util.*;
import java.util.*;
/**
 * The Logger takes Logs and outputs them using the choosen handler
 * @author    Scott Bell
 */

public class LoggerImpl extends LoggerPOA  {

	public void processLog(Log logToProcdess){
		System.out.println("Log: "+logToProcdess.getHead().getValue());
	}
}

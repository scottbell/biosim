package biosim.server.util;

import biosim.idl.util.*;
import java.util.*;
/**
 * The Log is essentially a tree containing strings.  It's used by the Logger to output to various data formats.
 * @author    Scott Bell
 */

public class LogImpl extends LogPOA  {
	//The head of the Log tree
	private LogNodeImpl headNode;
	
	/**
	* Creates a Log with a head node
	* @param topNode the name of the top node (like AirRS, Crew, etc)
	*/
	public LogImpl(org.omg.CORBA.Object topNodeValue){
		headNode = new LogNodeImpl(topNodeValue, null);
	}
	
	public LogNode getHead(){
		return LogNodeHelper.narrow((OrbUtils.poaToCorbaObj(headNode)));
	}
}

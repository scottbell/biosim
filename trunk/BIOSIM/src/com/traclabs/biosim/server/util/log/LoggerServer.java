package biosim.server.util.log;

import biosim.server.framework.*;
import biosim.idl.util.log.*;
/**
 * The Logger Server.  Creates an instance of the Logger and registers it with the nameserver.
 *
 * @author    Scott Bell
 */

public class LoggerServer extends GenericServer{
	/**
	* Instantiates the server and binds it to the name server.
	* @param args grabs ID from first element
	*/
	public static void main(String args[]) {
		LoggerServer myServer = new LoggerServer();
		LoggerImpl myLoggerImpl = new LoggerImpl(myServer.getIDfromArgs(args));
		myLoggerImpl.addLogHandlerType(LogHandlerType.XML);
		myServer.registerServerAndRun(myLoggerImpl, myLoggerImpl.getName(), myLoggerImpl.getID());
	}
}

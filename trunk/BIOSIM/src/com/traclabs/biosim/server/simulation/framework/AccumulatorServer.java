package biosim.server.framework;

import biosim.idl.framework.*;
/**
 * The Sim Environment Server.  Creates an instance of the Sim Environment and registers it with the nameserver.
 *
 * @author    Scott Bell
 */

public class AccumulatorServer extends GenericServer{
	
	/**
	* Instantiates the server and binds it to the name server.
	* @param args aren't used for anything
	*/
	public static void main(String args[]) {
		AccumulatorServer myServer = new AccumulatorServer();
		AccumulatorImpl myAccumulator = new AccumulatorImpl(myServer.getIDfromArgs(args));
		myServer.registerServerAndRun(new AccumulatorPOATie(myAccumulator), myAccumulator.getModuleName());
	}
}


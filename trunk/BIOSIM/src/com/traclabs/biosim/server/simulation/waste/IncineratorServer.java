package biosim.server.simulation.waste;

import biosim.server.framework.*;
import biosim.idl.simulation.waste.*;
/**
 * The Incinerator Server.  Creates an instance of the Incinerator  and registers it with the nameserver.
 *
 * @author    Scott Bell
 */

public class IncineratorServer extends GenericServer{
	
	/**
	* Instantiates the server and binds it to the name server.
	* @param args aren't used for anything
	*/
	public static void main(String args[]) {
		IncineratorServer myServer = new IncineratorServer();
		IncineratorImpl myIncinerator = new IncineratorImpl(myServer.getIDfromArgs(args), myServer.getNamefromArgs(args));
		myServer.registerServerAndRun(new IncineratorPOATie(myIncinerator), myIncinerator.getModuleName(), myIncinerator.getID());
	}
}


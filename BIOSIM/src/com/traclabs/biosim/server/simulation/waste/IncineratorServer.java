package biosim.server.simulation.waste;

import biosim.idl.simulation.waste.IncineratorPOATie;
import biosim.server.framework.GenericServer;
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
		IncineratorImpl myIncinerator = new IncineratorImpl(GenericServer.getIDfromArgs(args), GenericServer.getNamefromArgs(args));
		myServer.registerServerAndRun(new IncineratorPOATie(myIncinerator), myIncinerator.getModuleName(), myIncinerator.getID());
	}
}


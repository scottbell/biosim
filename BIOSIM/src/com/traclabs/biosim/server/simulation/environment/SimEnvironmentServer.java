package biosim.server.simulation.environment;

import biosim.idl.simulation.environment.SimEnvironmentPOATie;
import biosim.server.framework.GenericServer;
/**
 * The Sim Environment Server.  Creates an instance of the Sim Environment and registers it with the nameserver.
 *
 * @author    Scott Bell
 */

public class SimEnvironmentServer extends GenericServer{
	
	/**
	* Instantiates the server and binds it to the name server.
	* @param args aren't used for anything
	*/
	public static void main(String args[]) {
		SimEnvironmentServer myServer = new SimEnvironmentServer();
		SimEnvironmentImpl mySimEnvironment = new SimEnvironmentImpl(GenericServer.getIDfromArgs(args), GenericServer.getNamefromArgs(args));
		myServer.registerServerAndRun(new SimEnvironmentPOATie(mySimEnvironment), mySimEnvironment.getModuleName(), mySimEnvironment.getID());
	}
}


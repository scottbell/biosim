package biosim.server.framework;

import biosim.idl.simulation.framework.*;
import biosim.server.framework.*;
import biosim.server.simulation.framework.*;
/**
 * The Sim Environment Server.  Creates an instance of the Sim Environment and registers it with the nameserver.
 *
 * @author    Scott Bell
 */

public class InjectorServer extends GenericServer{
	
	/**
	* Instantiates the server and binds it to the name server.
	* @param args aren't used for anything
	*/
	public static void main(String args[]) {
		InjectorServer myServer = new InjectorServer();
		InjectorImpl myInjector = new InjectorImpl(myServer.getIDfromArgs(args));
		myServer.registerServerAndRun(new InjectorPOATie(myInjector), myInjector.getModuleName());
	}
}


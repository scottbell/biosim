package biosim.server.simulation.air;

import biosim.idl.simulation.air.*;
import biosim.server.framework.*;
/**
 * The O2 Store Server.  Creates an instance of the O2Store and registers it with the nameserver.
 *
 * @author    Scott Bell
 */
public class O2StoreServer extends GenericServer{
	
	/**
	* Instantiates the server and binds it to the name server.
	* @param args aren't used for anything
	*/
	public static void main(String args[]) {
		O2StoreServer myServer = new O2StoreServer();
		O2StoreImpl myO2Store = new O2StoreImpl(myServer.getIDfromArgs(args));
		myServer.registerServerAndRun(new O2StorePOATie(myO2Store), myO2Store.getModuleName());
	}
}


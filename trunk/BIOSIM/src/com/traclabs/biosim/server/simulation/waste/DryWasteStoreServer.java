package biosim.server.simulation.waste;

import biosim.server.framework.*;
import biosim.idl.simulation.waste.*;
/**
 * The DryWaste Store Server.  Creates an instance of the DryWaste Store and registers it with the nameserver.
 *
 * @author    Scott Bell
 */

public class DryWasteStoreServer extends GenericServer{
	
	/**
	* Instantiates the server and binds it to the name server.
	* @param args aren't used for anything
	*/
	public static void main(String args[]) {
		DryWasteStoreServer myServer = new DryWasteStoreServer();
		DryWasteStoreImpl myDryWasteStore = new DryWasteStoreImpl(myServer.getIDfromArgs(args), myServer.getNamefromArgs(args));
		myServer.registerServerAndRun(new DryWasteStorePOATie(myDryWasteStore), myDryWasteStore.getModuleName(), myDryWasteStore.getID());
	}
}

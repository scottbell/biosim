package biosim.server.simulation.food;

import biosim.server.framework.*;
import biosim.idl.simulation.food.*;
/**
 * The Biomass Store Server.  Creates an instance of the BiomassStore and registers it with the nameserver.
 *
 * @author    Scott Bell
 */

public class BiomassStoreServer extends GenericServer{
	
	/**
	* Instantiates the server and binds it to the name server.
	* @param args aren't used for anything
	*/
	public static void main(String args[]) {
		BiomassStoreServer myServer = new BiomassStoreServer();
		BiomassStoreImpl myBiomassStore = new BiomassStoreImpl(myServer.getIDfromArgs(args), myServer.getNamefromArgs(args));
		myServer.registerServerAndRun(new BiomassStorePOATie(myBiomassStore), myBiomassStore.getModuleName(), myBiomassStore.getID());
	}
}



package biosim.server.simulation.water;

import biosim.server.framework.*;
import biosim.idl.simulation.water.*;
/**
 * The Grey Water Store Server.  Creates an instance of the Grey Water Store and registers it with the nameserver.
 *
 * @author    Scott Bell
 */

public class GreyWaterStoreServer extends GenericServer{
	
	/**
	* Instantiates the server and binds it to the name server.
	* @param args aren't used for anything
	*/
	public static void main(String args[]) {
		GreyWaterStoreServer myServer = new GreyWaterStoreServer();
		GreyWaterStoreImpl myGreyWaterStore = new GreyWaterStoreImpl(myServer.getIDfromArgs(args), myServer.getNamefromArgs(args));
		myServer.registerServerAndRun(new GreyWaterStorePOATie(myGreyWaterStore), myGreyWaterStore.getModuleName(), myGreyWaterStore.getID());
	}
}


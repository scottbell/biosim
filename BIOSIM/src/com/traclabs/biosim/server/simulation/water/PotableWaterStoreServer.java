package biosim.server.simulation.water;

import biosim.server.framework.*;
import biosim.idl.simulation.water.*;
/**
 * The Potable Water Store Server.  Creates an instance of the Potable Water Store and registers it with the nameserver.
 *
 * @author    Scott Bell
 */

public class PotableWaterStoreServer extends GenericServer{
	
	/**
	* Instantiates the server and binds it to the name server.
	* @param args aren't used for anything
	*/
	public static void main(String args[]) {
		PotableWaterStoreServer myServer = new PotableWaterStoreServer();
		PotableWaterStoreImpl myPotableWaterStoreImpl = new PotableWaterStoreImpl(myServer.getIDfromArgs(args));
		myServer.registerServerAndRun(new PotableWaterStorePOATie(myPotableWaterStoreImpl), myPotableWaterStoreImpl.getModuleName());
	}
}


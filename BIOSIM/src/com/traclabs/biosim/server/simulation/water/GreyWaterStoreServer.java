package biosim.server.water;

import biosim.server.framework.*;
import biosim.idl.water.*;
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
		GreyWaterStoreImpl myGreyWaterStore = new GreyWaterStoreImpl(myServer.getIDfromArgs(args));
		myServer.registerServerAndRun(new GreyWaterStorePOATie(myGreyWaterStore), myGreyWaterStore.getModuleName());
	}
}


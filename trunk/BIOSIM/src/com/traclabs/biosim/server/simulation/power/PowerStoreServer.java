package biosim.server.simulation.power;

import biosim.server.framework.*;
import biosim.idl.simulation.power.*;
/**
 * The Power Store Server.  Creates an instance of the Power Store and registers it with the nameserver.
 *
 * @author    Scott Bell
 */

public class PowerStoreServer extends GenericServer{
	
	/**
	* Instantiates the server and binds it to the name server.
	* @param args aren't used for anything
	*/
	public static void main(String args[]) {
		PowerStoreServer myServer = new PowerStoreServer();
		PowerStoreImpl myPowerStore = new PowerStoreImpl(myServer.getIDfromArgs(args), myServer.getNamefromArgs(args));
		myServer.registerServerAndRun(new PowerStorePOATie(myPowerStore), myPowerStore.getModuleName());
	}
}


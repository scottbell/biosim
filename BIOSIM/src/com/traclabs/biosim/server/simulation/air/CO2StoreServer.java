package biosim.server.simulation.air;

import biosim.idl.simulation.air.*;
import biosim.server.framework.*;
/**
 * The CO2 Store Server.  Creates an instance of the CO2Store and registers it with the nameserver.
 *
 * @author    Scott Bell
 */

public class CO2StoreServer extends GenericServer{
	
	/**
	* Instantiates the server and binds it to the name server.
	* @param args aren't used for anything
	*/
	public static void main(String args[]) {
		CO2StoreServer myServer = new CO2StoreServer();
		CO2StoreImpl myCO2Store = new CO2StoreImpl(myServer.getIDfromArgs(args), myServer.getNamefromArgs(args));
		myServer.registerServerAndRun(new CO2StorePOATie(myCO2Store), myCO2Store.getModuleName(), myCO2Store.getID());
	}
}


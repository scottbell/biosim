package biosim.server.simulation.air;

import biosim.idl.simulation.air.CO2StorePOATie;
import biosim.server.framework.GenericServer;
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
		CO2StoreImpl myCO2Store = new CO2StoreImpl(GenericServer.getIDfromArgs(args), GenericServer.getNamefromArgs(args));
		myServer.registerServerAndRun(new CO2StorePOATie(myCO2Store), myCO2Store.getModuleName(), myCO2Store.getID());
	}
}


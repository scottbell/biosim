package biosim.server.simulation.water;

import biosim.server.framework.*;
import biosim.idl.simulation.water.*;
/**
 * The Water Recovery System Server.  Creates an instance of the Water Recovery System and registers it with the nameserver.
 *
 * @author    Scott Bell
 */

public class WaterRSServer extends GenericServer{
	
	/**
	* Instantiates the server and binds it to the name server.
	* @param args aren't used for anything
	*/
	public static void main(String args[]) {
		WaterRSServer myServer = new WaterRSServer();
		WaterRSImpl myWaterRS = new WaterRSImpl(myServer.getIDfromArgs(args), myServer.getNamefromArgs(args));
		myServer.registerServerAndRun(new WaterRSPOATie(myWaterRS), myWaterRS.getModuleName(), myWaterRS.getID());
	}
}

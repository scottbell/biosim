package biosim.server.water;

import biosim.server.framework.*;
import biosim.idl.water.*;
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
		WaterRSImpl myWaterRSImpl = new WaterRSImpl(myServer.getIDfromArgs(args));
		myServer.registerServerAndRun(new WaterRSPOATie(myWaterRSImpl), myWaterRSImpl.getModuleName());
	}
}


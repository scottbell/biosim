package biosim.server.simulation.power;

import biosim.server.framework.*;
import biosim.idl.simulation.power.*;
/**
 * The Power PS Server.  Creates an instance of the Power PS and registers it with the nameserver.
 *
 * @author    Scott Bell
 */

public class PowerPSServer extends GenericServer{
	
	/**
	* Instantiates the server and binds it to the name server.
	* @param args aren't used for anything
	*/
	public static void main(String args[]) {
		PowerPSServer myServer = new PowerPSServer();
		PowerPSImpl myPowerPS = new SolarPowerPS(myServer.getIDfromArgs(args));
		myServer.registerServerAndRun(new PowerPSPOATie(myPowerPS), myPowerPS.getModuleName());
	}
}


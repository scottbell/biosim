package biosim.server.simulation.air;

import biosim.idl.simulation.air.AirRSPOATie;
import biosim.server.framework.GenericServer;
/**
 * The Air Revitalization System Server.  Creates an instance of the AirRS and registers it with the nameserver.
 *
 * @author    Scott Bell
 */

public class AirRSServer extends GenericServer{
	
	/**
	* Instantiates the server and binds it to the name server.
	* @param args aren't used for anything
	*/
	public static void main(String args[]) {
		AirRSServer myServer = new AirRSServer();
		AirRSImpl myAirRS = new AirRSImpl(GenericServer.getIDfromArgs(args), GenericServer.getNamefromArgs(args));
		myServer.registerServerAndRun(new AirRSPOATie(myAirRS), myAirRS.getModuleName(), myAirRS.getID());
	}
}


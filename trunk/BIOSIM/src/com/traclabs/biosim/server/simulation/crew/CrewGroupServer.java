package biosim.server.simulation.crew;

import biosim.server.framework.*;
import biosim.idl.simulation.crew.*;
/**
 * The Crew Server.  Creates an instance of the Crew and registers it with the nameserver.
 *
 * @author    Scott Bell
 */

public class CrewGroupServer extends GenericServer{
	
	/**
	* Instantiates the server and binds it to the name server.
	* @param args aren't used for anything
	*/
	public static void main(String args[]) {
		CrewGroupServer myServer = new CrewGroupServer();
		CrewGroupImpl myCrewGroup = new CrewGroupImpl(myServer.getIDfromArgs(args), myServer.getNamefromArgs(args));
		myServer.registerServerAndRun(new CrewGroupPOATie(myCrewGroup), myCrewGroup.getModuleName());
	}
}


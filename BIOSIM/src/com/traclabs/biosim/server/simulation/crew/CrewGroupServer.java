package biosim.server.crew;

import biosim.server.framework.*;
import biosim.idl.crew.*;
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
		CrewGroupImpl myCrewGroup = new CrewGroupImpl(0);
		myServer.registerServerAndRun(new CrewGroupPOATie(myCrewGroup), myCrewGroup.getModuleName());
	}
}


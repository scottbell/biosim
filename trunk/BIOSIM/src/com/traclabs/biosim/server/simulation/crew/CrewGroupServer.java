package com.traclabs.biosim.server.simulation.crew;

import com.traclabs.biosim.idl.simulation.crew.CrewGroupPOATie;
import com.traclabs.biosim.server.framework.GenericServer;
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
		CrewGroupImpl myCrewGroup = new CrewGroupImpl(GenericServer.getIDfromArgs(args), GenericServer.getNamefromArgs(args));
		myServer.registerServerAndRun(new CrewGroupPOATie(myCrewGroup), myCrewGroup.getModuleName(), myCrewGroup.getID());
	}
}


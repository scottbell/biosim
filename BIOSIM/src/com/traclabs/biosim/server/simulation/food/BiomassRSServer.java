package com.traclabs.biosim.server.simulation.food;

import com.traclabs.biosim.idl.simulation.food.BiomassRSPOATie;
import com.traclabs.biosim.server.framework.GenericServer;
/**
 * The BiomassRS System Server.  Creates an instance of the BiomassRS and registers it with the nameserver.
 *
 * @author    Scott Bell
 */

public class BiomassRSServer extends GenericServer{
	
	/**
	* Instantiates the server and binds it to the name server.
	* @param args aren't used for anything
	*/
	public static void main(String args[]) {
		BiomassRSServer myServer = new BiomassRSServer();
		BiomassRSImpl myBiomassRS = new BiomassRSImpl(GenericServer.getIDfromArgs(args), GenericServer.getNamefromArgs(args));
		myServer.registerServerAndRun(new BiomassRSPOATie(myBiomassRS), myBiomassRS.getModuleName(), myBiomassRS.getID());
	}
}


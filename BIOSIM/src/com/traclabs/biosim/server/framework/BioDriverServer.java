package biosim.server.framework;

import biosim.server.framework.*;
/**
 * The Driver Server.  
 *
 * @author    Scott Bell
 */

public class BioDriverServer extends GenericServer{
	
	/**
	* Instantiates the server and binds it to the name server.
	* @param args first element can be the ID of the server
	*/
	public static void main(String args[]) {
		BioDriverServer myServer = new BioDriverServer();
		BioDriverImpl myBioDriver = new BioDriverImpl(GenericServer.getIDfromArgs(args));
		myServer.registerServerAndRun(myBioDriver, myBioDriver.getName(), myBioDriver.getID());
	}
}


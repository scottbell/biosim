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
	* @param args aren't used for anything
	*/
	public static void main(String args[]) {
		BioDriverServer myServer = new BioDriverServer();
		BioDriverImpl myBioDriver = new BioDriverImpl(0);
		myServer.registerServerAndRun(myBioDriver, myBioDriver.getName());
	}
}


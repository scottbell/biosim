package biosim.server.framework;

import java.net.*;

/**
 * The Biosim Server.  Creates an instance of each module (AirRS, FoodProcessor, WaterRS, etc..) and binds them to the nameserver.
 *
 * @author    Scott Bell
 */

public class BiosimServer extends GenericServer{

	private BioInitializer myInitializer = new BioInitializer();

	public BiosimServer(){
		URL documentUrl = ClassLoader.getSystemClassLoader().getResource("biosim/server/framework/DefaultInitialization.xml");
		myInitializer = new BioInitializer();
		String documentString = documentUrl.toString();
		if (documentString.length() > 0)
			myInitializer.parseFile(documentString);
	}

	/**
	* Instantiates the server and binds it to the name server.
	* @param args first element can be an ID to assign to this instance
	*/
	public static void main(String args[]) {
		BiosimServer myServer = new BiosimServer();
		myServer.runServer("BiosimServer");
	}
}


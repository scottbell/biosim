package biosim.server.framework;

import java.net.*;
import biosim.server.framework.*;
import biosim.server.util.log.*;

/**
 * The Biosim Server.  Creates an instance of each module (AirRS, FoodProcessor, WaterRS, etc..) and binds them to the nameserver.
 *
 * @author    Scott Bell
 */

public class BiosimServer extends GenericServer{
	
	public BiosimServer(int id){
		URL documentUrl = ClassLoader.getSystemClassLoader().getResource("biosim/server/framework/DefaultInitialization.xml");
		LoggerImpl myLoggerImpl = new LoggerImpl(id, "Logger");
		BioDriverImpl myBioDriverImpl = new BioDriverImpl(id, "BioDriver");
		registerServer(myLoggerImpl, myLoggerImpl.getName(), myLoggerImpl.getID());
		registerServer(myBioDriverImpl, myBioDriverImpl.getName(), myBioDriverImpl.getID());
		BioInitializer myInitializer = new BioInitializer(id);
		String documentString = documentUrl.toString();
		if (documentString.length() > 0)
			myInitializer.parseFile(documentString);
	}

	/**
	* Instantiates the server and binds it to the name server.
	* @param args first element can be an ID to assign to this instance
	*/
	public static void main(String args[]) {
		int id = BiosimServer.getIDfromArgs(args);
		BiosimServer server = new BiosimServer(id);
		server.runServer("BiosimServer"+id);
	}
}


package biosim.server.framework;

import java.net.*;
import biosim.server.framework.*;
import biosim.server.util.log.*;
import biosim.idl.util.log.*;
import biosim.server.util.*;

/**
 * The Biosim Server.  Creates an instance of each module (AirRS, FoodProcessor, WaterRS, etc..) and binds them to the nameserver.
 *
 * @author    Scott Bell
 */

public class BiosimServer extends GenericServer{
	
	public BiosimServer(int id, int stutterLength){
		URL documentUrl = ClassLoader.getSystemClassLoader().getResource("biosim/server/framework/DefaultInitialization.xml");
		LoggerImpl myLoggerImpl = new LoggerImpl(id);
		myLoggerImpl.addLogHandlerType(LogHandlerType.XML);
		BioDriverImpl myBioDriverImpl = new BioDriverImpl(id);
		myBioDriverImpl.setDriverStutterLength(stutterLength);
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
		BiosimServer server = new BiosimServer(id, 0);
		server.runServer("BiosimServer (id="+id+")");
	}
}


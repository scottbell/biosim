package biosim.server.framework;

import java.net.URL;

import biosim.server.util.log.LoggerImpl;

/**
 * The Biosim Server.  Creates an instance of each module (AirRS, FoodProcessor, WaterRS, etc..) and binds them to the nameserver.
 *
 * @author    Scott Bell
 */

public class BiosimServer extends GenericServer{
	
	public BiosimServer(int id, int stutterLength, String xmlLocation){
		URL documentUrl = ClassLoader.getSystemClassLoader().getResource(xmlLocation);
		if (documentUrl == null){
			System.err.println("Couldn't find init xml file: "+xmlLocation);
			System.err.println("Exiting...");
			System.exit(1);
		}
		System.out.println("Loading init file: "+documentUrl);
		LoggerImpl myLoggerImpl = new LoggerImpl(id);
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
		String xmlLocation = BiosimServer.getXMLfromArgs(args);
		BiosimServer server = new BiosimServer(id, 0, xmlLocation);
		server.runServer("BiosimServer (id="+id+")");
	}
}


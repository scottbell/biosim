package com.traclabs.biosim.server.framework;

import org.apache.log4j.Logger;

import com.traclabs.biosim.util.OrbUtils;


/**
 * The Biosim Server. Creates an instance of each module (AirRS, FoodProcessor,
 * WaterRS, etc..) and binds them to the nameserver.
 * 
 * @author Scott Bell
 */

public class BiosimServer extends GenericServer {
	private static final String DEFAULT_XML_LOCATION = "com/traclabs/biosim/server/framework/DefaultInit.xml";

    public BiosimServer(int id, int stutterLength, String xmlLocation) {
        String rawFileLocation = resolveXMLLocation(xmlLocation);
        if (rawFileLocation == null) {
            myLogger.error("Couldn't find init xml file: " + xmlLocation);
            return;
        }
        BioDriverImpl newBioDriverImpl = new BioDriverImpl(id);
        newBioDriverImpl.setDriverStutterLength(stutterLength);
        registerServer(newBioDriverImpl, newBioDriverImpl.getName(),
        		newBioDriverImpl.getID());
        myLogger.info("Loading init file: " + rawFileLocation);
        BioInitializer myInitializer = new BioInitializer(id);
        myInitializer.parseFile(rawFileLocation);
    }

	/**
     * Instantiates the server and binds it to the name server.
     * 
     * @param args
     *            first element can be an ID to assign to this instance
     */
    public static void main(String[] args) {
    	OrbUtils.initializeLog(false);
        int id = GenericServer.getIDfromArgs(args);
        String xmlLocation = GenericServer.getXMLfromArgs(args);
        if (xmlLocation == null){
        	Logger.getLogger(BiosimServer.class).info("using default XML");
        	xmlLocation = DEFAULT_XML_LOCATION;
        }
        int stutterLength = 0;
        if (getDemoFromArgs(args)){
            stutterLength = 300;
        }
        BiosimServer server = new BiosimServer(id, stutterLength, xmlLocation);
        server.runServer("BiosimServer (id=" + id + ")");
    }

    protected static boolean getDemoFromArgs(String[] myArgs) {
        for (int i = 0; i < myArgs.length; i++) {
            if (myArgs[i].equals("-demo"))
                return true;
        }
        return false;
    }
}


package com.traclabs.biosim.server.framework;

import org.apache.log4j.Logger;

import com.traclabs.biosim.util.CommandLineUtils;
import com.traclabs.biosim.util.OrbUtils;


/**
 * The Biosim Server. Creates an instance of each module (AirRS, FoodProcessor,
 * WaterRS, etc..) and binds them to the nameserver.
 * 
 * @author Scott Bell
 */

public class BiosimServer extends GenericServer {
	private static final String DEFAULT_XML_LOCATION = "com/traclabs/biosim/server/framework/configuration/default.biosim";
	private BioDriverImpl myBioDriverImpl;
	
    public BiosimServer(int id, int stutterLength, String xmlLocation) {
        String rawFileLocation = OrbUtils.resolveXMLLocation(xmlLocation);
        if (rawFileLocation == null) {
            myLogger.warn("Couldn't find init xml file: " + xmlLocation);
            myLogger.warn("Using default: "+DEFAULT_XML_LOCATION);
            rawFileLocation = OrbUtils.resolveXMLLocation(DEFAULT_XML_LOCATION);
        }
        
        myLogger.info("Loading init file: " + rawFileLocation);
        BiosimInitializer myInitializer = new BiosimInitializer(id);
        myInitializer.parseFile(rawFileLocation);
    }
    
    public BioDriverImpl getBioDriverImpl(){
    	return myBioDriverImpl;
    }

	/**
     * Instantiates the server and binds it to the name server.
     * 
     * @param args
     *            first element can be an ID to assign to this instance
     */
    public static void main(String[] args) {
    	OrbUtils.initializeLog(false);
        int id = CommandLineUtils.getIDfromArgs(args);
        String xmlLocation = CommandLineUtils.getXMLfromArgs(args);
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


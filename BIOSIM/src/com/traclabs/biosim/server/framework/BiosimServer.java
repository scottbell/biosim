package com.traclabs.biosim.server.framework;

import java.net.URL;

import org.apache.log4j.Logger;

/**
 * The Biosim Server. Creates an instance of each module (AirRS, FoodProcessor,
 * WaterRS, etc..) and binds them to the nameserver.
 * 
 * @author Scott Bell
 */

public class BiosimServer extends GenericServer {

    public BiosimServer(int id, int stutterLength, String xmlLocation) {
        URL documentUrl = ClassLoader.getSystemClassLoader().getResource(
                xmlLocation);
        if (documentUrl == null) {
            myLogger.error("Couldn't find init xml file: " + xmlLocation);
            return;
        }
        BioDriverImpl newBioDriverImpl = new BioDriverImpl(id);
        newBioDriverImpl.setDriverStutterLength(stutterLength);
        registerServer(newBioDriverImpl, newBioDriverImpl.getName(),
        		newBioDriverImpl.getID());
        myLogger.info("Loading init file: " + documentUrl);
        BioInitializer myInitializer = new BioInitializer(id);
        String documentString = documentUrl.toString();
        if (documentString.length() > 0)
            myInitializer.parseFile(documentString);
            
    }

    /**
     * Instantiates the server and binds it to the name server.
     * 
     * @param args
     *            first element can be an ID to assign to this instance
     */
    public static void main(String[] args) {
        int id = GenericServer.getIDfromArgs(args);
        String xmlLocation = GenericServer.getXMLfromArgs(args);
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


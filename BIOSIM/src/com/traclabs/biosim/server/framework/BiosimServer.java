package com.traclabs.biosim.server.framework;

import java.net.URL;

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
            myLogger.error("Exiting...");
            return;
        }
        myLogger.info("Loading init file: " + documentUrl);
        BioDriverImpl myBioDriverImpl = new BioDriverImpl(id);
        myBioDriverImpl.setDriverStutterLength(stutterLength);
        registerServer(myBioDriverImpl, myBioDriverImpl.getName(),
                myBioDriverImpl.getID());
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
    public static void main(String args[]) {
        int id = BiosimServer.getIDfromArgs(args);
        String xmlLocation = BiosimServer.getXMLfromArgs(args);
        BiosimServer server = new BiosimServer(id, 0, xmlLocation);
        server.runServer("BiosimServer (id=" + id + ")");
    }
}


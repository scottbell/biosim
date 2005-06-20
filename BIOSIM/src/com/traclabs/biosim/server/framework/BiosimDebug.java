package com.traclabs.biosim.server.framework;

import org.apache.log4j.Logger;

import com.traclabs.biosim.util.OrbUtils;

/**
 * A standalone BioSim instance (server, nameserver, client in one) for
 * debugging
 * 
 * @author Scott Bell
 */

public class BiosimDebug {
    private Logger myLogger;

    public BiosimDebug() {
        myLogger = Logger.getLogger(BiosimDebug.class.toString());
    }

    public static void main(String args[]) {
        OrbUtils.initializeLog();
        BiosimDebug myBiosimStandalone = new BiosimDebug();
        int id = GenericServer.getIDfromArgs(args);
        String xmlLocation = GenericServer.getXMLfromArgs(args);
        myBiosimStandalone.beginSimulation(id, xmlLocation);
    }

    public void beginSimulation(int id, String xmlLocation) {
        OrbUtils.startDebugNameServer();
        try {
            myLogger.info("Sleeping until nameserver comes online...");
            Thread.sleep(5000);
        } catch (Exception e) {
        }
        
        OrbUtils.initializeServerForDebug();
        
        myLogger.info("Server awake...");
        BiosimServer myBiosimServer = new BiosimServer(id, 0, xmlLocation);
        myBiosimServer.runServer("BiosimServer (id=0)");
    }
}
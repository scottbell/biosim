package com.traclabs.biosim.server.framework;

import com.traclabs.biosim.util.CommandLineUtils;

/**
 * The Driver Server.
 * 
 * @author Scott Bell
 */

public class BioDriverServer extends GenericServer {

    /**
     * Instantiates the server and binds it to the name server.
     * 
     * @param args
     *            first element can be the ID of the server
     */
    public static void main(String args[]) {
        BioDriverServer myServer = new BioDriverServer();
        BioDriverImpl myBioDriver = new BioDriverImpl(CommandLineUtils
                .getIDfromArgs(args));
        myServer.registerServerAndRun(myBioDriver, myBioDriver.getName(),
                myBioDriver.getID());
    }
}


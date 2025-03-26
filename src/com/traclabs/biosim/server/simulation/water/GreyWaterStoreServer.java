package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.server.framework.GenericServer;
import com.traclabs.biosim.server.simulation.water.GreyWaterStorePOATie;
import com.traclabs.biosim.util.CommandLineUtils;

/**
 * The Grey Water Store Server. Creates an instance of the Grey Water Store and
 * registers it with the nameserver.
 * 
 * @author Scott Bell
 */

public class GreyWaterStoreServer extends GenericServer {

    /**
     * Instantiates the server and binds it to the name server.
     * 
     * @param args
     *            aren't used for anything
     */
    public static void main(String args[]) {
        GreyWaterStoreServer myServer = new GreyWaterStoreServer();
        GreyWaterStore myGreyWaterStore = new GreyWaterStore(
        		CommandLineUtils.getIDfromArgs(args), CommandLineUtils
                        .getNamefromArgs(args));
        myServer.registerServerAndRun(
                new GreyWaterStorePOATie(myGreyWaterStore), myGreyWaterStore
                        .getModuleName(), myGreyWaterStore.getID());
    }
}


package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.server.framework.GenericServer;
import com.traclabs.biosim.server.simulation.water.PotableWaterStorePOATie;
import com.traclabs.biosim.util.CommandLineUtils;

/**
 * The Potable Water Store Server. Creates an instance of the Potable Water
 * Store and registers it with the nameserver.
 * 
 * @author Scott Bell
 */

public class PotableWaterStoreServer extends GenericServer {

    /**
     * Instantiates the server and binds it to the name server.
     * 
     * @param args
     *            aren't used for anything
     */
    public static void main(String args[]) {
        PotableWaterStoreServer myServer = new PotableWaterStoreServer();
        PotableWaterStore myPotableWaterStore = new PotableWaterStore(
        		CommandLineUtils.getIDfromArgs(args), CommandLineUtils
                        .getNamefromArgs(args));
        myServer.registerServerAndRun(new PotableWaterStorePOATie(
                myPotableWaterStore), myPotableWaterStore.getModuleName(),
                myPotableWaterStore.getID());
    }
}


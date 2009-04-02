package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.idl.simulation.water.DirtyWaterStorePOATie;
import com.traclabs.biosim.server.framework.GenericServer;
import com.traclabs.biosim.util.CommandLineUtils;

/**
 * The Dirty Water Store Server. Creates an instance of the Dirty Water Store
 * and registers it with the nameserver.
 * 
 * @author Scott Bell
 */

public class DirtyWaterStoreServer extends GenericServer {

    /**
     * Instantiates the server and binds it to the name server.
     * 
     * @param args
     *            aren't used for anything
     */
    public static void main(String args[]) {
        DirtyWaterStoreServer myServer = new DirtyWaterStoreServer();
        DirtyWaterStoreImpl myDirtyWaterStore = new DirtyWaterStoreImpl(
        		CommandLineUtils.getIDfromArgs(args), CommandLineUtils
                        .getNamefromArgs(args));
        myServer.registerServerAndRun(new DirtyWaterStorePOATie(
                myDirtyWaterStore), myDirtyWaterStore.getModuleName(),
                myDirtyWaterStore.getID());
    }
}


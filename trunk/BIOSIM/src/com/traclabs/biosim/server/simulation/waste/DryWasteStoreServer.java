package com.traclabs.biosim.server.simulation.waste;

import com.traclabs.biosim.idl.simulation.waste.DryWasteStorePOATie;
import com.traclabs.biosim.server.framework.GenericServer;

/**
 * The DryWaste Store Server. Creates an instance of the DryWaste Store and
 * registers it with the nameserver.
 * 
 * @author Scott Bell
 */

public class DryWasteStoreServer extends GenericServer {

    /**
     * Instantiates the server and binds it to the name server.
     * 
     * @param args
     *            aren't used for anything
     */
    public static void main(String args[]) {
        DryWasteStoreServer myServer = new DryWasteStoreServer();
        DryWasteStoreImpl myDryWasteStore = new DryWasteStoreImpl(GenericServer
                .getIDfromArgs(args), GenericServer.getNamefromArgs(args));
        myServer.registerServerAndRun(new DryWasteStorePOATie(myDryWasteStore),
                myDryWasteStore.getModuleName(), myDryWasteStore.getID());
    }
}


package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.idl.simulation.water.WaterRSPOATie;
import com.traclabs.biosim.server.framework.GenericServer;
import com.traclabs.biosim.util.CommandLineUtils;

/**
 * The Water Recovery System Server. Creates an instance of the Water Recovery
 * System and registers it with the nameserver.
 * 
 * @author Scott Bell
 */

public class WaterRSServer extends GenericServer {

    /**
     * Instantiates the server and binds it to the name server.
     * 
     * @param args
     *            aren't used for anything
     */
    public static void main(String args[]) {
        WaterRSServer myServer = new WaterRSServer();
        WaterRSImpl myWaterRS = new WaterRSImpl(CommandLineUtils
                .getIDfromArgs(args), CommandLineUtils.getNamefromArgs(args));
        myServer.registerServerAndRun(new WaterRSPOATie(myWaterRS), myWaterRS
                .getModuleName(), myWaterRS.getID());
    }
}


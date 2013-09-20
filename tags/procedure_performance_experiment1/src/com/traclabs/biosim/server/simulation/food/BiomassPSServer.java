package com.traclabs.biosim.server.simulation.food;

import com.traclabs.biosim.idl.simulation.food.BiomassPSPOATie;
import com.traclabs.biosim.server.framework.GenericServer;
import com.traclabs.biosim.util.CommandLineUtils;

/**
 * The BiomassPS System Server. Creates an instance of the BiomassPS and
 * registers it with the nameserver.
 * 
 * @author Scott Bell
 */

public class BiomassPSServer extends GenericServer {

    /**
     * Instantiates the server and binds it to the name server.
     * 
     * @param args
     *            aren't used for anything
     */
    public static void main(String args[]) {
        BiomassPSServer myServer = new BiomassPSServer();
        BiomassPSImpl myBiomassPS = new BiomassPSImpl(CommandLineUtils
                .getIDfromArgs(args), CommandLineUtils.getNamefromArgs(args));
        myServer.registerServerAndRun(new BiomassPSPOATie(myBiomassPS),
                myBiomassPS.getModuleName(), myBiomassPS.getID());
    }
}


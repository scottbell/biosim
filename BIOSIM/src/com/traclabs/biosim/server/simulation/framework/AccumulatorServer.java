package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.framework.AccumulatorPOATie;
import com.traclabs.biosim.server.framework.GenericServer;

/**
 * The Accumulator Server. Creates an instance of the Sim Environment and
 * registers it with the nameserver.
 * 
 * @author Scott Bell
 */

public class AccumulatorServer extends GenericServer {

    /**
     * Instantiates the server and binds it to the name server.
     * 
     * @param args
     *            first argument checked for ID
     */
    public static void main(String args[]) {
        AccumulatorServer myServer = new AccumulatorServer();
        AccumulatorImpl myAccumulator = new AccumulatorImpl(GenericServer
                .getIDfromArgs(args), GenericServer.getNamefromArgs(args));
        myServer.registerServerAndRun(new AccumulatorPOATie(myAccumulator),
                myAccumulator.getModuleName(), myAccumulator.getID());
    }
}


package com.traclabs.biosim.server.simulation.framework;

/**
 * The basic Accumulator implementation. Can be configured to take any modules
 * as input, and any modules as output. It takes as much as it can (max taken
 * set by maxFlowRates) from one module and pushes it into another module.
 * Functionally equivalent to an Accumulator at this point.
 *
 * @author Scott Bell
 */

public class Accumulator extends ResourceMover {

    public Accumulator() {
        this(0, "Unnamed Accumulator");
    }

    public Accumulator(int pID, String pName) {
        super(pID, pName);
    }

}
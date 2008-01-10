package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.framework.AccumulatorOperations;

/**
 * The basic Accumulator Implementation. Can be configured to take any modules
 * as input, and any modules as output. It takes as much as it can (max taken
 * set by maxFlowRates) from one module and pushes it into another module.
 * Functionally equivalent to an Accumulator at this point.
 * 
 * @author Scott Bell
 */

public class AccumulatorImpl extends ResourceMover implements AccumulatorOperations{
	
    public AccumulatorImpl() {
    	this(0, "Unnamed Accumulator");
    }

	public AccumulatorImpl(int pID, String pName) {
		super(pID, pName);
	}

}
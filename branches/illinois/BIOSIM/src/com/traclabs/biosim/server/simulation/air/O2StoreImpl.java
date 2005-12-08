package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.idl.simulation.air.O2StoreOperations;
import com.traclabs.biosim.server.simulation.framework.StoreImpl;

/**
 * The O2 Store Implementation. Used by the AirRS to store excess O2 for the
 * crew. Not really used right now.
 * 
 * @author Scott Bell
 */
public class O2StoreImpl extends StoreImpl implements O2StoreOperations {
    public O2StoreImpl(int pID, String pName) {
        super(pID, pName);
    }
}
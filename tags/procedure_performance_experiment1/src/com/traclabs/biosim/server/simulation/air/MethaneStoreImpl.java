package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.idl.simulation.air.MethaneStoreOperations;
import com.traclabs.biosim.server.simulation.framework.StoreImpl;

/**
 * The Methane Store Implementation. Used by the AirRS to store excess Methane for the
 * crew. Not really used right now.
 * 
 * @author Scott Bell
 */
public class MethaneStoreImpl extends StoreImpl implements MethaneStoreOperations {
    public MethaneStoreImpl(int pID, String pName) {
        super(pID, pName);
    }
}
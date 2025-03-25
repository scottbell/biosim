package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.simulation.air.MethaneStoreOperations;
import com.traclabs.biosim.server.simulation.framework.Store;

/**
 * The Methane Store ementation. Used by the AirRS to store excess Methane for the
 * crew. Not really used right now.
 * 
 * @author Scott Bell
 */
public class MethaneStore extends Store implements MethaneStoreOperations {
    public MethaneStore(int pID, String pName) {
        super(pID, pName);
    }
}
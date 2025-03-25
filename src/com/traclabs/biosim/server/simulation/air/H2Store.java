package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.simulation.air.H2StoreOperations;
import com.traclabs.biosim.server.simulation.framework.Store;

/**
 * The H2 Store ementation. Used by the AirRS to store excess H2 for the
 * crew. Not really used right now.
 * 
 * @author Scott Bell
 */
public class H2Store extends Store implements H2StoreOperations {
    public H2Store(int pID, String pName) {
        super(pID, pName);
    }
}
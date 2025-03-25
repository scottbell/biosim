package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.simulation.air.CO2StoreOperations;
import com.traclabs.biosim.server.simulation.framework.Store;

/**
 * The CO2 Store ementation. Used by the AirRS to store excess CO2 for
 * plants. Not really used right now.
 * 
 * @author Scott Bell
 */

public class CO2Store extends Store implements CO2StoreOperations {

    public CO2Store(int pID, String pName) {
        super(pID, pName);
    }

}
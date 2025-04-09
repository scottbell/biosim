package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.simulation.framework.Store;

/**
 * The CO2 Store implementation. Used by the AirRS to store excess CO2 for
 * plants. Not really used right now.
 *
 * @author Scott Bell
 */

public class CO2Store extends Store implements ICO2Store {

    public CO2Store(int pID, String pName) {
        super(pID, pName);
    }

}
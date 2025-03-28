package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.simulation.framework.Store;

/**
 * The H2 Store implementation. Used by the AirRS to store excess H2 for the
 * crew. Not really used right now.
 *
 * @author Scott Bell
 */
public class H2Store extends Store {
    public H2Store(int pID, String pName) {
        super(pID, pName);
    }
}
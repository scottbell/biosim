package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.simulation.framework.Store;

/**
 * The O2 Store implementation. Used by the AirRS to store excess O2 for the
 * crew. Not really used right now.
 *
 * @author Scott Bell
 */
public class O2Store extends Store implements IO2Store {
    public O2Store(int pID, String pName) {
        super(pID, pName);
    }
}
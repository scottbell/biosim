package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.simulation.framework.Store;

/**
 * The Nitrogen Store implementation. Used by the AirRS to store excess Nitrogen
 * for the crew. Not really used right now.
 *
 * @author Scott Bell
 */
public class NitrogenStore extends Store implements INitrogenStore {
    public NitrogenStore(int pID, String pName) {
        super(pID, pName);
    }
}
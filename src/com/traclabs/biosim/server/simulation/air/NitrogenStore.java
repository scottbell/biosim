package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.simulation.air.NitrogenStoreOperations;
import com.traclabs.biosim.server.simulation.framework.Store;

/**
 * The Nitrogen Store ementation. Used by the AirRS to store excess Nitrogen
 * for the crew. Not really used right now.
 * 
 * @author Scott Bell
 */
public class NitrogenStore extends Store implements
        NitrogenStoreOperations {
    public NitrogenStore(int pID, String pName) {
        super(pID, pName);
    }
}
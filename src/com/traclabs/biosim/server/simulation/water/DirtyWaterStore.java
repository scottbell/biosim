package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.server.simulation.water.DirtyWaterStoreOperations;

/**
 * The Dirty Water Store ementation. Takes dirty water from the crew members
 * and stores it for either purification by the Water RS.
 * 
 * @author Scott Bell
 */

public class DirtyWaterStore extends WaterStore implements
        DirtyWaterStoreOperations {
    public DirtyWaterStore(int pID, String pName) {
        super(pID, pName);
    }

	public DirtyWaterStore() {
		this(0, "Unnamed DirtyWaterStore");
	}
}
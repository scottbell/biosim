package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.server.simulation.water.PotableWaterStoreOperations;

/**
 * The Potable Water Store ementation. Filled with clean potable water from
 * the WaterRS and used by the crew memebers to drink.
 * 
 * @author Scott Bell
 */

public class PotableWaterStore extends WaterStore implements
        PotableWaterStoreOperations {
    public PotableWaterStore(int pID, String pName) {
        super(pID, pName);
    }

	public PotableWaterStore() {
		this(0, "Unnamed PotableWaterStore");
	}
}
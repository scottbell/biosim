package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.server.simulation.water.GreyWaterStoreOperations;

/**
 * The Grey Water Store ementation. Takes dirty water from the crew members
 * and stores it for either purification by the WaterRS or use for the crops (in
 * the Biomass RS).
 * 
 * @author Scott Bell
 */

public class GreyWaterStore extends WaterStore implements
        GreyWaterStoreOperations {
    public GreyWaterStore(int pID, String pName) {
        super(pID, pName);
    }

	public GreyWaterStore() {
		this(0, "Unnamed GreyWaterStore");
	}
}
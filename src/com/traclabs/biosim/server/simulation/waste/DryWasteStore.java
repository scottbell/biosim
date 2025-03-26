package com.traclabs.biosim.server.simulation.waste;

import com.traclabs.biosim.server.simulation.framework.Store;
import com.traclabs.biosim.server.simulation.waste.DryWasteStoreOperations;

/**
 * The DryWaste Store ementation.
 * 
 * @author Scott Bell
 */

public class DryWasteStore extends Store implements
        DryWasteStoreOperations {
    public DryWasteStore(int pID, String pName) {
        super(pID, pName);
    }

	public DryWasteStore() {
		this(0, "Unnamed Dry Waste Store");
	}
}
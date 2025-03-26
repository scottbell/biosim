package com.traclabs.biosim.server.simulation.waste;

import com.traclabs.biosim.server.simulation.framework.Store;

/**
 * The DryWaste Store implementation.
 * 
 * @author Scott Bell
 */

public class DryWasteStore extends Store {
    public DryWasteStore(int pID, String pName) {
        super(pID, pName);
    }

	public DryWasteStore() {
		this(0, "Unnamed Dry Waste Store");
	}
}
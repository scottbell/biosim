package com.traclabs.biosim.server.simulation.waste;

import com.traclabs.biosim.idl.simulation.waste.DryWasteStoreOperations;
import com.traclabs.biosim.server.simulation.framework.StoreImpl;

/**
 * The DryWaste Store Implementation.
 * 
 * @author Scott Bell
 */

public class DryWasteStoreImpl extends StoreImpl implements
        DryWasteStoreOperations {
    public DryWasteStoreImpl(int pID, String pName) {
        super(pID, pName);
    }

	public DryWasteStoreImpl() {
		this(0, "Unnamed Dry Waste Store");
	}
}
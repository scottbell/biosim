package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.idl.simulation.water.DirtyWaterStoreOperations;

/**
 * The Dirty Water Store Implementation. Takes dirty water from the crew members
 * and stores it for either purification by the Water RS.
 * 
 * @author Scott Bell
 */

public class DirtyWaterStoreImpl extends WaterStoreImpl implements
        DirtyWaterStoreOperations {
    public DirtyWaterStoreImpl(int pID, String pName) {
        super(pID, pName);
    }

	public DirtyWaterStoreImpl() {
		this(0, "Unnamed DirtyWaterStore");
	}
}
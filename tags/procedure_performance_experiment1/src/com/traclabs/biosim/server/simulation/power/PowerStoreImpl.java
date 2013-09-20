package com.traclabs.biosim.server.simulation.power;

import com.traclabs.biosim.idl.simulation.power.PowerStoreOperations;
import com.traclabs.biosim.server.simulation.framework.StoreImpl;

/**
 * The Power Store Implementation. Takes power from the Power Production System
 * and stores it here for other modules to use.
 * 
 * @author Scott Bell
 */

public class PowerStoreImpl extends StoreImpl implements PowerStoreOperations {
    public PowerStoreImpl(){
    	this(0, "Unnamed PowerStoreImpl");
    }
	
	public PowerStoreImpl(int pID, String pName) {
        super(pID, pName);
    }
}
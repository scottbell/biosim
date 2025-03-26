package com.traclabs.biosim.server.simulation.power;

import com.traclabs.biosim.server.simulation.framework.Store;

/**
 * The Power Store ementation. Takes power from the Power Production System
 * and stores it here for other modules to use.
 * 
 * @author Scott Bell
 */

public class PowerStore extends Store {
    public PowerStore(){
    	this(0, "Unnamed PowerStore");
    }
	
	public PowerStore(int pID, String pName) {
        super(pID, pName);
    }
}
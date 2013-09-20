package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.idl.simulation.air.CO2StoreOperations;
import com.traclabs.biosim.server.simulation.framework.StoreImpl;

/**
 * The CO2 Store Implementation. Used by the AirRS to store excess CO2 for
 * plants. Not really used right now.
 * 
 * @author Scott Bell
 */

public class CO2StoreImpl extends StoreImpl implements CO2StoreOperations {

    public CO2StoreImpl(int pID, String pName) {
        super(pID, pName);
    }

}
package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.idl.simulation.air.NitrogenStoreOperations;
import com.traclabs.biosim.server.simulation.framework.StoreImpl;

/**
 * The Nitrogen Store Implementation. Used by the AirRS to store excess Nitrogen
 * for the crew. Not really used right now.
 * 
 * @author Scott Bell
 */
public class NitrogenStoreImpl extends StoreImpl implements
        NitrogenStoreOperations {
    public NitrogenStoreImpl(int pID, String pName) {
        super(pID, pName);
    }
}
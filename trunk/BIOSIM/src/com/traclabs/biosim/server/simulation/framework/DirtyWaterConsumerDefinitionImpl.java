package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.framework.DirtyWaterConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStore;

/**
 * @author Scott Bell
 */

public class DirtyWaterConsumerDefinitionImpl extends StoreFlowRateControllableImpl implements DirtyWaterConsumerDefinitionOperations {
    public void setDirtyWaterInputs(DirtyWaterStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
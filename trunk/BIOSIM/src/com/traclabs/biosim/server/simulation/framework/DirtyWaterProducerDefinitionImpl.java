package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.framework.DirtyWaterProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStore;

/**
 * @author Scott Bell
 */

public class DirtyWaterProducerDefinitionImpl extends StoreFlowRateControllableImpl implements DirtyWaterProducerDefinitionOperations {
    public void setDirtyWaterOutputs(DirtyWaterStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
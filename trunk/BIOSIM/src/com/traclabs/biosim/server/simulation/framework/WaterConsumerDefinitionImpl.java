package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.framework.WaterConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.water.WaterStore;

/**
 * @author Scott Bell
 */

public class WaterConsumerDefinitionImpl extends StoreFlowRateControllableImpl implements WaterConsumerDefinitionOperations {
    public void setWaterInputs(WaterStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
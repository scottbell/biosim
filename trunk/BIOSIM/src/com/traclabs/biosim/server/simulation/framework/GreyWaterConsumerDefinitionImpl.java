package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.framework.GreyWaterConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.water.GreyWaterStore;

/**
 * @author Scott Bell
 */

public class GreyWaterConsumerDefinitionImpl extends StoreFlowRateControllableImpl implements GreyWaterConsumerDefinitionOperations {
    public void setGreyWaterInputs(GreyWaterStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
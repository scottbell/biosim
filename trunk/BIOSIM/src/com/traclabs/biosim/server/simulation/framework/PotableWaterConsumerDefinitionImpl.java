package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.framework.PotableWaterConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStore;

/**
 * @author Scott Bell
 */

public class PotableWaterConsumerDefinitionImpl extends StoreFlowRateControllableImpl implements PotableWaterConsumerDefinitionOperations {
    public void setPotableWaterInputs(PotableWaterStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
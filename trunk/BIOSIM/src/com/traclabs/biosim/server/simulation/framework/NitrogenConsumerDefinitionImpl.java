package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.air.NitrogenStore;
import com.traclabs.biosim.idl.simulation.framework.NitrogenConsumerDefinitionOperations;

/**
 * @author Scott Bell
 */

public class NitrogenConsumerDefinitionImpl extends StoreFlowRateControllableImpl implements NitrogenConsumerDefinitionOperations {
    public void setNitrogenInputs(NitrogenStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
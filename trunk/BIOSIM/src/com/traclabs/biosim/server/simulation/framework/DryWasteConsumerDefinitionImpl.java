package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.framework.DryWasteConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.waste.DryWasteStore;

/**
 * @author Scott Bell
 */

public class DryWasteConsumerDefinitionImpl extends StoreFlowRateControllableImpl implements DryWasteConsumerDefinitionOperations {
    public void setDryWasteInputs(DryWasteStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.framework.DryWasteProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.waste.DryWasteStore;

/**
 * @author Scott Bell
 */

public class DryWasteProducerDefinitionImpl extends StoreFlowRateControllableImpl implements DryWasteProducerDefinitionOperations {
    public void setDryWasteOutputs(DryWasteStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
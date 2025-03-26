package com.traclabs.biosim.server.simulation.waste;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;

/**
 * @author Scott Bell
 */

public class DryWasteConsumerDefinition extends
        StoreFlowRateControllable {

    public DryWasteConsumerDefinition(BioModule pModule) {
super(pModule);
    }

    public void setDryWasteInputs(DryWasteStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
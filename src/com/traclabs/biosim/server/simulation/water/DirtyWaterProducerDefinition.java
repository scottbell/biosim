package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;

/**
 * @author Scott Bell
 */

public class DirtyWaterProducerDefinition extends
        StoreFlowRateControllable {

    public DirtyWaterProducerDefinition(BioModule pModule) {
     super(pModule);
    }

    public void setDirtyWaterOutputs(DirtyWaterStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
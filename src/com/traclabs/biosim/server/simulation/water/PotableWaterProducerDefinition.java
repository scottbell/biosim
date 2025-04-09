package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;

/**
 * @author Scott Bell
 */

public class PotableWaterProducerDefinition extends
        StoreFlowRateControllable {

    public PotableWaterProducerDefinition(BioModule pModule) {
        super(pModule);
    }

    public void setPotableWaterOutputs(PotableWaterStore[] pStores,
                                       float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
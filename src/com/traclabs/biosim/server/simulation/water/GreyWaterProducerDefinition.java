package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.server.framework.BioModule;

/**
 * @author Scott Bell
 */

public class GreyWaterProducerDefinition extends
        WaterProducerDefinition {

    public GreyWaterProducerDefinition(BioModule pModule) {
        super(pModule);
    }

    public void setGreyWaterOutputs(GreyWaterStore[] pStores,
                                    float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
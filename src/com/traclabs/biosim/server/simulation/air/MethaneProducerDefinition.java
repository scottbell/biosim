package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;

/**
 * @author Scott Bell
 */

public class MethaneProducerDefinition extends
        StoreFlowRateControllable {
    private MethaneProducerDefinition myMethaneProducerDefinition;

    public MethaneProducerDefinition(BioModule pModule) {
     super(pModule);
    }

    public void setMethaneOutputs(MethaneStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
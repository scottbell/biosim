package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;

/**
 * @author Scott Bell
 */

public class O2ConsumerDefinition extends StoreFlowRateControllable {

    public O2ConsumerDefinition(BioModule pModule) {
        super(pModule);
    }

    public void setO2Inputs(IO2Store[] pStores, float[] pMaxFlowRates,
                            float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
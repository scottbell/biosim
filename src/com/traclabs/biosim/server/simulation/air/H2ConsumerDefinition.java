package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;

/**
 * @author Scott Bell
 */

public class H2ConsumerDefinition extends StoreFlowRateControllable {

    public H2ConsumerDefinition(BioModule pModule) {
        super(pModule);
    }

    public void setH2Inputs(H2Store[] pStores, float[] pMaxFlowRates,
                            float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
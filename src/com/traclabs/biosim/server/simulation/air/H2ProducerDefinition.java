package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;

/**
 * @author Scott Bell
 */

public class H2ProducerDefinition extends StoreFlowRateControllable {
    private H2ProducerDefinition myH2ProducerDefinition;

    public H2ProducerDefinition(BioModule pModule) {
    	super(pModule);
    }

    public void setH2Outputs(H2Store[] pStores, float[] pMaxFlowRates,
            float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
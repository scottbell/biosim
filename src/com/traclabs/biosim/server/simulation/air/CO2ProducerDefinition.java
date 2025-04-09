package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;

/**
 * @author Scott Bell
 */

public class CO2ProducerDefinition extends StoreFlowRateControllable {

    public CO2ProducerDefinition(BioModule pModule) {
        super(pModule);
    }

    public void setCO2Outputs(ICO2Store[] pStores, float[] pMaxFlowRates,
                              float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;

/**
 * @author Scott Bell
 */

public class CO2ConsumerDefinition extends StoreFlowRateControllable {
    private CO2ConsumerDefinition myCO2ConsumerDefinition;

    public CO2ConsumerDefinition(BioModule pModule) {
super(pModule);
    }

    public void setCO2Inputs(ICO2Store[] pStores, float[] pMaxFlowRates,
            float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
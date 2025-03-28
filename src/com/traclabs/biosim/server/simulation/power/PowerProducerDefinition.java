package com.traclabs.biosim.server.simulation.power;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;

/**
 * @author Scott Bell
 */

public class PowerProducerDefinition extends StoreFlowRateControllable {

    public PowerProducerDefinition(BioModule pModule) {
        super(pModule);
    }

    public void setPowerOutputs(PowerStore[] pStores, float[] pMaxFlowRates,
                                float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
package com.traclabs.biosim.server.simulation.power;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;

/**
 * @author Scott Bell
 */

public class PowerConsumerDefinition extends StoreFlowRateControllable {

    public PowerConsumerDefinition(BioModule pModule) {
        super(pModule);
    }


    public void setPowerInputs(PowerStore[] pStores, float[] pMaxFlowRates,
                               float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
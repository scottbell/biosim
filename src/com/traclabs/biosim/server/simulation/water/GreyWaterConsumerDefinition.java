package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;

/**
 * @author Scott Bell
 */

public class GreyWaterConsumerDefinition extends
        StoreFlowRateControllable {

    public GreyWaterConsumerDefinition(BioModule pModule) {
        super(pModule);
    }

    public void setGreyWaterInputs(GreyWaterStore[] pStores,
                                   float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
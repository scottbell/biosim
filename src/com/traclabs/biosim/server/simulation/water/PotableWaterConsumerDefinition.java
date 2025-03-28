package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;

/**
 * @author Scott Bell
 */

public class PotableWaterConsumerDefinition extends
        StoreFlowRateControllable {
    private PotableWaterConsumerDefinition myPotableWaterConsumerDefinition;

    public PotableWaterConsumerDefinition(BioModule pModule) {
        super(pModule);
    }

    public void setPotableWaterInputs(PotableWaterStore[] pStores,
                                      float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
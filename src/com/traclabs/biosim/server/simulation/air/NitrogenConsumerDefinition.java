package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;

/**
 * @author Scott Bell
 */

public class NitrogenConsumerDefinition extends
        StoreFlowRateControllable {

    public NitrogenConsumerDefinition(BioModule pModule) {
        super(pModule);
    }

    public void setNitrogenInputs(INitrogenStore[] pStores,
                                  float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
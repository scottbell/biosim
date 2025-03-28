package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;

/**
 * @author Scott Bell
 */

public class NitrogenProducerDefinition extends
        StoreFlowRateControllable {

    public NitrogenProducerDefinition(BioModule pModule) {
        super(pModule);
    }

    public void setNitrogenOutputs(INitrogenStore[] pStores,
                                   float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
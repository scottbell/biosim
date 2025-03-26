package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;

/**
 * @author Scott Bell
 */

public class MethaneConsumerDefinition extends StoreFlowRateControllable  {
    private MethaneConsumerDefinition myMethaneConsumerDefinition;

    public MethaneConsumerDefinition(BioModule pModule) {
        super(pModule);
    }

    public MethaneConsumerDefinition getCorbaObject() {
        return myMethaneConsumerDefinition;
    }

    public void setMethaneInputs(MethaneStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
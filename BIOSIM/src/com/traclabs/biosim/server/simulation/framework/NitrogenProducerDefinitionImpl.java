package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.air.NitrogenStore;
import com.traclabs.biosim.idl.simulation.framework.NitrogenProducerDefinitionOperations;

/**
 * @author Scott Bell
 */

public class NitrogenProducerDefinitionImpl extends StoreFlowRateControllableImpl implements NitrogenProducerDefinitionOperations {
    public void setNitrogenOutputs(NitrogenStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
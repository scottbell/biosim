package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.framework.PowerProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.power.PowerStore;

/**
 * @author Scott Bell
 */

public class PowerProducerDefinitionImpl extends StoreFlowRateControllableImpl implements PowerProducerDefinitionOperations {
    public void setPowerOutputs(PowerStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
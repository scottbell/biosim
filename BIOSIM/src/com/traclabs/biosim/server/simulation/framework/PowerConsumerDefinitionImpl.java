package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.framework.PowerConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.power.PowerStore;

/**
 * @author Scott Bell
 */

public class PowerConsumerDefinitionImpl extends StoreFlowRateControllableImpl implements PowerConsumerDefinitionOperations {
    public void setPowerInputs(PowerStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
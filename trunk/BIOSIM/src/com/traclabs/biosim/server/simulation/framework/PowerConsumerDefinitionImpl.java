package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.framework.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.PowerConsumerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.framework.PowerConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.framework.PowerConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.power.PowerStore;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class PowerConsumerDefinitionImpl extends StoreFlowRateControllableImpl
        implements PowerConsumerDefinitionOperations {
    private PowerConsumerDefinition myPowerConsumerDefinition;

    public PowerConsumerDefinitionImpl() {
        myPowerConsumerDefinition = PowerConsumerDefinitionHelper
                .narrow(OrbUtils
                        .poaToCorbaObj(new PowerConsumerDefinitionPOATie(this)));
    }

    public PowerConsumerDefinition getCorbaObject() {
        return myPowerConsumerDefinition;
    }

    public void setPowerInputs(PowerStore[] pStores, float[] pMaxFlowRates,
            float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
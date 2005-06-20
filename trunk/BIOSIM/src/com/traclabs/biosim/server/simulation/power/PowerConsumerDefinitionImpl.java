package com.traclabs.biosim.server.simulation.power;

import com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.power.PowerStore;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.util.OrbUtils;

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
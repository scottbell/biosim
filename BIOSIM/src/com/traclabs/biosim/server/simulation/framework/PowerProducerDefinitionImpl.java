package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.framework.PowerProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.PowerProducerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.framework.PowerProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.framework.PowerProducerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.power.PowerStore;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class PowerProducerDefinitionImpl extends StoreFlowRateControllableImpl
        implements PowerProducerDefinitionOperations {
    private PowerProducerDefinition myPowerProducerDefinition;

    public PowerProducerDefinitionImpl() {
        myPowerProducerDefinition = PowerProducerDefinitionHelper
                .narrow(OrbUtils
                        .poaToCorbaObj(new PowerProducerDefinitionPOATie(this)));
    }

    public PowerProducerDefinition getCorbaObject() {
        return myPowerProducerDefinition;
    }

    public void setPowerOutputs(PowerStore[] pStores, float[] pMaxFlowRates,
            float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
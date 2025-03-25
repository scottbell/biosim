package com.traclabs.biosim.server.simulation.power;

import com.traclabs.biosim.server.simulation.power.PowerProducerDefinition;
import com.traclabs.biosim.server.simulation.power.PowerProducerDefinitionOperations;
import com.traclabs.biosim.server.simulation.power.PowerProducerDefinitionPOATie;
import com.traclabs.biosim.server.simulation.power.PowerStore;
import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class PowerProducerDefinition extends StoreFlowRateControllable
        implements PowerProducerDefinitionOperations {
    private PowerProducerDefinition myPowerProducerDefinition;

    public PowerProducerDefinition(BioModule pModule) {
    	super(pModule);
    	PowerProducerDefinitionPOATie tie = new PowerProducerDefinitionPOATie(this);
    	myPowerProducerDefinition = tie._this(OrbUtils.getORB());
    }

    public PowerProducerDefinition getCorbaObject() {
        return myPowerProducerDefinition;
    }

    public void setPowerOutputs(PowerStore[] pStores, float[] pMaxFlowRates,
            float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
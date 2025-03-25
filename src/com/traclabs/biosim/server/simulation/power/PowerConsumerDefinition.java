package com.traclabs.biosim.server.simulation.power;

import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinitionOperations;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinitionPOATie;
import com.traclabs.biosim.server.simulation.power.PowerStore;
import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class PowerConsumerDefinition extends StoreFlowRateControllable
        implements PowerConsumerDefinitionOperations {
    private PowerConsumerDefinition myPowerConsumerDefinition;

    public PowerConsumerDefinition(BioModule pModule) {
    	super(pModule);
    	PowerConsumerDefinitionPOATie tie = new PowerConsumerDefinitionPOATie(this);
    	myPowerConsumerDefinition = tie._this(OrbUtils.getORB());
    }

    public PowerConsumerDefinition getCorbaObject() {
        return myPowerConsumerDefinition;
    }

    public void setPowerInputs(PowerStore[] pStores, float[] pMaxFlowRates,
            float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
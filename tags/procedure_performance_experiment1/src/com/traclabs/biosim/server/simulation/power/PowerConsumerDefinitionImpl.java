package com.traclabs.biosim.server.simulation.power;

import com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.power.PowerStore;
import com.traclabs.biosim.server.framework.BioModuleImpl;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class PowerConsumerDefinitionImpl extends StoreFlowRateControllableImpl
        implements PowerConsumerDefinitionOperations {
    private PowerConsumerDefinition myPowerConsumerDefinition;

    public PowerConsumerDefinitionImpl(BioModuleImpl pModule) {
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
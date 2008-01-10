package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.water.GreyWaterStore;
import com.traclabs.biosim.server.framework.BioModuleImpl;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class GreyWaterConsumerDefinitionImpl extends
        StoreFlowRateControllableImpl implements
        GreyWaterConsumerDefinitionOperations {
    private GreyWaterConsumerDefinition myGreyWaterConsumerDefinition;

    public GreyWaterConsumerDefinitionImpl(BioModuleImpl pModule) {
super(pModule);

    	GreyWaterConsumerDefinitionPOATie tie = new GreyWaterConsumerDefinitionPOATie(this);
    	myGreyWaterConsumerDefinition = tie._this(OrbUtils.getORB());
    }

    public GreyWaterConsumerDefinition getCorbaObject() {
        return myGreyWaterConsumerDefinition;
    }

    public void setGreyWaterInputs(GreyWaterStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
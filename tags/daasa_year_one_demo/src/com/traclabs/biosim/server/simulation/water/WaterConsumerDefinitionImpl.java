package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.idl.simulation.water.WaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.water.WaterConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.water.WaterConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.water.WaterStore;
import com.traclabs.biosim.server.framework.BioModuleImpl;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class WaterConsumerDefinitionImpl extends StoreFlowRateControllableImpl
        implements WaterConsumerDefinitionOperations {
    private WaterConsumerDefinition myWaterConsumerDefinition;

    public WaterConsumerDefinitionImpl(BioModuleImpl pModule) {
super(pModule);

    	WaterConsumerDefinitionPOATie tie = new WaterConsumerDefinitionPOATie(this);
    	myWaterConsumerDefinition = tie._this(OrbUtils.getORB());
    }

    public WaterConsumerDefinition getCorbaObject() {
        return myWaterConsumerDefinition;
    }

    public void setWaterInputs(WaterStore[] pStores, float[] pMaxFlowRates,
            float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
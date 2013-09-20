package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.idl.simulation.water.DirtyWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStore;
import com.traclabs.biosim.server.framework.BioModuleImpl;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class DirtyWaterConsumerDefinitionImpl extends
        StoreFlowRateControllableImpl implements
        DirtyWaterConsumerDefinitionOperations {
    private DirtyWaterConsumerDefinition myDirtyWaterConsumerDefinition;

    public DirtyWaterConsumerDefinitionImpl(BioModuleImpl pModule) {
super(pModule);

    	DirtyWaterConsumerDefinitionPOATie tie = new DirtyWaterConsumerDefinitionPOATie(this);
    	myDirtyWaterConsumerDefinition = tie._this(OrbUtils.getORB());
    }

    public DirtyWaterConsumerDefinition getCorbaObject() {
        return myDirtyWaterConsumerDefinition;
    }

    public void setDirtyWaterInputs(DirtyWaterStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
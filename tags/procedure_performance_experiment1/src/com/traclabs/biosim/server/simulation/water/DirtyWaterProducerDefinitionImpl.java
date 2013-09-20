package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.idl.simulation.water.DirtyWaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterProducerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStore;
import com.traclabs.biosim.server.framework.BioModuleImpl;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class DirtyWaterProducerDefinitionImpl extends
        StoreFlowRateControllableImpl implements
        DirtyWaterProducerDefinitionOperations {
    private DirtyWaterProducerDefinition myDirtyWaterProducerDefinition;

    public DirtyWaterProducerDefinitionImpl(BioModuleImpl pModule) {
     super(pModule);    	DirtyWaterProducerDefinitionPOATie tie = new DirtyWaterProducerDefinitionPOATie(this);
    	myDirtyWaterProducerDefinition = tie._this(OrbUtils.getORB());
    }

    public DirtyWaterProducerDefinition getCorbaObject() {
        return myDirtyWaterProducerDefinition;
    }

    public void setDirtyWaterOutputs(DirtyWaterStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
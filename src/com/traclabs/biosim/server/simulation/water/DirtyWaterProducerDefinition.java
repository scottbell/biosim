package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.server.simulation.water.DirtyWaterProducerDefinition;
import com.traclabs.biosim.server.simulation.water.DirtyWaterProducerDefinitionOperations;
import com.traclabs.biosim.server.simulation.water.DirtyWaterProducerDefinitionPOATie;
import com.traclabs.biosim.server.simulation.water.DirtyWaterStore;
import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class DirtyWaterProducerDefinition extends
        StoreFlowRateControllable implements
        DirtyWaterProducerDefinitionOperations {
    private DirtyWaterProducerDefinition myDirtyWaterProducerDefinition;

    public DirtyWaterProducerDefinition(BioModule pModule) {
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
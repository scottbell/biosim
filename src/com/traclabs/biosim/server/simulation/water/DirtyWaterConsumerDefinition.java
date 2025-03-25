package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.server.simulation.water.DirtyWaterConsumerDefinition;
import com.traclabs.biosim.server.simulation.water.DirtyWaterConsumerDefinitionOperations;
import com.traclabs.biosim.server.simulation.water.DirtyWaterConsumerDefinitionPOATie;
import com.traclabs.biosim.server.simulation.water.DirtyWaterStore;
import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class DirtyWaterConsumerDefinition extends
        StoreFlowRateControllable implements
        DirtyWaterConsumerDefinitionOperations {
    private DirtyWaterConsumerDefinition myDirtyWaterConsumerDefinition;

    public DirtyWaterConsumerDefinition(BioModule pModule) {
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
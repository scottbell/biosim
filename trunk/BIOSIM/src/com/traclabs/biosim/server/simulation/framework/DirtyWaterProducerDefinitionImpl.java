package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.framework.DirtyWaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.DirtyWaterProducerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.framework.DirtyWaterProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.framework.DirtyWaterProducerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStore;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class DirtyWaterProducerDefinitionImpl extends
        StoreFlowRateControllableImpl implements
        DirtyWaterProducerDefinitionOperations {
    private DirtyWaterProducerDefinition myDirtyWaterProducerDefinition;

    public DirtyWaterProducerDefinitionImpl() {
        myDirtyWaterProducerDefinition = DirtyWaterProducerDefinitionHelper
                .narrow(OrbUtils
                        .poaToCorbaObj(new DirtyWaterProducerDefinitionPOATie(
                                this)));
    }

    public DirtyWaterProducerDefinition getCorbaObject() {
        return myDirtyWaterProducerDefinition;
    }

    public void setDirtyWaterOutputs(DirtyWaterStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
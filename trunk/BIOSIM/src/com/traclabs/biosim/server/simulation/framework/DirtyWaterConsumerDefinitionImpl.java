package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.framework.DirtyWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.DirtyWaterConsumerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.framework.DirtyWaterConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.framework.DirtyWaterConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStore;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class DirtyWaterConsumerDefinitionImpl extends
        StoreFlowRateControllableImpl implements
        DirtyWaterConsumerDefinitionOperations {
    private DirtyWaterConsumerDefinition myDirtyWaterConsumerDefinition;

    public DirtyWaterConsumerDefinitionImpl() {
        myDirtyWaterConsumerDefinition = DirtyWaterConsumerDefinitionHelper
                .narrow(OrbUtils
                        .poaToCorbaObj(new DirtyWaterConsumerDefinitionPOATie(
                                this)));
    }

    public DirtyWaterConsumerDefinition getCorbaObject() {
        return myDirtyWaterConsumerDefinition;
    }

    public void setDirtyWaterInputs(DirtyWaterStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
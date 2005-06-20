package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.idl.simulation.water.DirtyWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterConsumerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStore;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.util.OrbUtils;

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
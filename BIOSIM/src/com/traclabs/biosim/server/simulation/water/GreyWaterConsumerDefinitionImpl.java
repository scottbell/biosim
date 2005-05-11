package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.water.GreyWaterStore;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class GreyWaterConsumerDefinitionImpl extends
        StoreFlowRateControllableImpl implements
        GreyWaterConsumerDefinitionOperations {
    private GreyWaterConsumerDefinition myGreyWaterConsumerDefinition;

    public GreyWaterConsumerDefinitionImpl() {
        myGreyWaterConsumerDefinition = GreyWaterConsumerDefinitionHelper
                .narrow(OrbUtils
                        .poaToCorbaObj(new GreyWaterConsumerDefinitionPOATie(
                                this)));
    }

    public GreyWaterConsumerDefinition getCorbaObject() {
        return myGreyWaterConsumerDefinition;
    }

    public void setGreyWaterInputs(GreyWaterStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.framework.GreyWaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.GreyWaterProducerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.framework.GreyWaterProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.framework.GreyWaterProducerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.water.GreyWaterStore;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class GreyWaterProducerDefinitionImpl extends
        StoreFlowRateControllableImpl implements
        GreyWaterProducerDefinitionOperations {
    private GreyWaterProducerDefinition myGreyWaterProducerDefinition;

    public GreyWaterProducerDefinitionImpl() {
        myGreyWaterProducerDefinition = GreyWaterProducerDefinitionHelper
                .narrow(OrbUtils
                        .poaToCorbaObj(new GreyWaterProducerDefinitionPOATie(
                                this)));
    }

    public GreyWaterProducerDefinition getCorbaObject() {
        return myGreyWaterProducerDefinition;
    }

    public void setGreyWaterOutputs(GreyWaterStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
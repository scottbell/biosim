package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.framework.PotableWaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterProducerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterProducerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStore;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class PotableWaterProducerDefinitionImpl extends
        StoreFlowRateControllableImpl implements
        PotableWaterProducerDefinitionOperations {
    private PotableWaterProducerDefinition myPotableWaterProducerDefinition;

    public PotableWaterProducerDefinitionImpl() {
        myPotableWaterProducerDefinition = PotableWaterProducerDefinitionHelper
                .narrow(OrbUtils
                        .poaToCorbaObj(new PotableWaterProducerDefinitionPOATie(
                                this)));
    }

    public PotableWaterProducerDefinition getCorbaObject() {
        return myPotableWaterProducerDefinition;
    }

    public void setPotableWaterOutputs(PotableWaterStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
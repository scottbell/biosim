package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.framework.PotableWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterConsumerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStore;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class PotableWaterConsumerDefinitionImpl extends
        StoreFlowRateControllableImpl implements
        PotableWaterConsumerDefinitionOperations {
    private PotableWaterConsumerDefinition myPotableWaterConsumerDefinition;

    public PotableWaterConsumerDefinitionImpl() {
        myPotableWaterConsumerDefinition = PotableWaterConsumerDefinitionHelper
                .narrow(OrbUtils
                        .poaToCorbaObj(new PotableWaterConsumerDefinitionPOATie(
                                this)));
    }

    public PotableWaterConsumerDefinition getCorbaObject() {
        return myPotableWaterConsumerDefinition;
    }

    public void setPotableWaterInputs(PotableWaterStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
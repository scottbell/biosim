package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.idl.simulation.water.WaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.water.WaterProducerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.water.WaterProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.water.WaterProducerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.water.WaterStore;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class WaterProducerDefinitionImpl extends StoreFlowRateControllableImpl
        implements WaterProducerDefinitionOperations {
    private WaterProducerDefinition myWaterProducerDefinition;

    public WaterProducerDefinitionImpl() {
        myWaterProducerDefinition = WaterProducerDefinitionHelper
                .narrow(OrbUtils
                        .poaToCorbaObj(new WaterProducerDefinitionPOATie(this)));
    }

    public WaterProducerDefinition getCorbaObject() {
        return myWaterProducerDefinition;
    }

    public void setWaterOutputs(WaterStore[] pStores, float[] pMaxFlowRates,
            float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
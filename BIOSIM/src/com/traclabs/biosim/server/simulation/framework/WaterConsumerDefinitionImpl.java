package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.framework.WaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.WaterConsumerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.framework.WaterConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.framework.WaterConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.water.WaterStore;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class WaterConsumerDefinitionImpl extends StoreFlowRateControllableImpl
        implements WaterConsumerDefinitionOperations {
    private WaterConsumerDefinition myWaterConsumerDefinition;

    public WaterConsumerDefinitionImpl() {
        myWaterConsumerDefinition = WaterConsumerDefinitionHelper
                .narrow(OrbUtils
                        .poaToCorbaObj(new WaterConsumerDefinitionPOATie(this)));
    }

    public WaterConsumerDefinition getCorbaObject() {
        return myWaterConsumerDefinition;
    }

    public void setWaterInputs(WaterStore[] pStores, float[] pMaxFlowRates,
            float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
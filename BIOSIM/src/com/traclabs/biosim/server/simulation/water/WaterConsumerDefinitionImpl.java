package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.idl.simulation.water.WaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.water.WaterConsumerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.water.WaterConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.water.WaterConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.water.WaterStore;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.util.OrbUtils;

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
package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;
import com.traclabs.biosim.server.simulation.water.WaterConsumerDefinitionOperations;
import com.traclabs.biosim.server.simulation.water.WaterConsumerDefinitionPOATie;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class WaterConsumerDefinition extends StoreFlowRateControllable
        implements WaterConsumerDefinitionOperations {
    private WaterConsumerDefinition myWaterConsumerDefinition;

    public WaterConsumerDefinition(BioModule pModule) {
super(pModule);

    	WaterConsumerDefinitionPOATie tie = new WaterConsumerDefinitionPOATie(this);
    	myWaterConsumerDefinition = tie._this(OrbUtils.getORB());
    }

    public WaterConsumerDefinition getCorbaObject() {
        return myWaterConsumerDefinition;
    }

    public void setWaterInputs(WaterStore[] pStores, float[] pMaxFlowRates,
            float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
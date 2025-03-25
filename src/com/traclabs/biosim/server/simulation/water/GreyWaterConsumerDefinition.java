package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.server.simulation.water.GreyWaterConsumerDefinition;
import com.traclabs.biosim.server.simulation.water.GreyWaterConsumerDefinitionOperations;
import com.traclabs.biosim.server.simulation.water.GreyWaterConsumerDefinitionPOATie;
import com.traclabs.biosim.server.simulation.water.GreyWaterStore;
import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class GreyWaterConsumerDefinition extends
        StoreFlowRateControllable implements
        GreyWaterConsumerDefinitionOperations {
    private GreyWaterConsumerDefinition myGreyWaterConsumerDefinition;

    public GreyWaterConsumerDefinition(BioModule pModule) {
super(pModule);

    	GreyWaterConsumerDefinitionPOATie tie = new GreyWaterConsumerDefinitionPOATie(this);
    	myGreyWaterConsumerDefinition = tie._this(OrbUtils.getORB());
    }

    public GreyWaterConsumerDefinition getCorbaObject() {
        return myGreyWaterConsumerDefinition;
    }

    public void setGreyWaterInputs(GreyWaterStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
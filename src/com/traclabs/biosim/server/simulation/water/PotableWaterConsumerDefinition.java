package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.server.simulation.water.PotableWaterConsumerDefinition;
import com.traclabs.biosim.server.simulation.water.PotableWaterConsumerDefinitionOperations;
import com.traclabs.biosim.server.simulation.water.PotableWaterConsumerDefinitionPOATie;
import com.traclabs.biosim.server.simulation.water.PotableWaterStore;
import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class PotableWaterConsumerDefinition extends
        StoreFlowRateControllable implements
        PotableWaterConsumerDefinitionOperations {
    private PotableWaterConsumerDefinition myPotableWaterConsumerDefinition;

    public PotableWaterConsumerDefinition(BioModule pModule) {
super(pModule);

    	PotableWaterConsumerDefinitionPOATie tie = new PotableWaterConsumerDefinitionPOATie(this);
    	myPotableWaterConsumerDefinition = tie._this(OrbUtils.getORB());
    }

    public PotableWaterConsumerDefinition getCorbaObject() {
        return myPotableWaterConsumerDefinition;
    }

    public void setPotableWaterInputs(PotableWaterStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
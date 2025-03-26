package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;
import com.traclabs.biosim.server.simulation.water.PotableWaterProducerDefinitionOperations;
import com.traclabs.biosim.server.simulation.water.PotableWaterProducerDefinitionPOATie;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class PotableWaterProducerDefinition extends
        StoreFlowRateControllable implements
        PotableWaterProducerDefinitionOperations {
    private PotableWaterProducerDefinition myPotableWaterProducerDefinition;

    public PotableWaterProducerDefinition(BioModule pModule) {
     super(pModule);    	PotableWaterProducerDefinitionPOATie tie = new PotableWaterProducerDefinitionPOATie(this);
    	myPotableWaterProducerDefinition = tie._this(OrbUtils.getORB());
    }

    public PotableWaterProducerDefinition getCorbaObject() {
        return myPotableWaterProducerDefinition;
    }

    public void setPotableWaterOutputs(PotableWaterStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
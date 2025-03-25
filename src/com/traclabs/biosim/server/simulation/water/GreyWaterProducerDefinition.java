package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.server.simulation.water.GreyWaterProducerDefinition;
import com.traclabs.biosim.server.simulation.water.GreyWaterProducerDefinitionOperations;
import com.traclabs.biosim.server.simulation.water.GreyWaterProducerDefinitionPOATie;
import com.traclabs.biosim.server.simulation.water.GreyWaterStore;
import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class GreyWaterProducerDefinition extends
        WaterProducerDefinition implements
        GreyWaterProducerDefinitionOperations {
    private GreyWaterProducerDefinition myGreyWaterProducerDefinition;

    public GreyWaterProducerDefinition(BioModule pModule) {
     super(pModule);    	GreyWaterProducerDefinitionPOATie tie = new GreyWaterProducerDefinitionPOATie(this);
    	myGreyWaterProducerDefinition = tie._this(OrbUtils.getORB());
    }

    public GreyWaterProducerDefinition getCorbaObject() {
        return myGreyWaterProducerDefinition;
    }

    public void setGreyWaterOutputs(GreyWaterStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
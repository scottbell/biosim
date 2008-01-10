package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.idl.simulation.water.GreyWaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.water.GreyWaterProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.water.GreyWaterProducerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.water.GreyWaterStore;
import com.traclabs.biosim.server.framework.BioModuleImpl;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class GreyWaterProducerDefinitionImpl extends
        StoreFlowRateControllableImpl implements
        GreyWaterProducerDefinitionOperations {
    private GreyWaterProducerDefinition myGreyWaterProducerDefinition;

    public GreyWaterProducerDefinitionImpl(BioModuleImpl pModule) {
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
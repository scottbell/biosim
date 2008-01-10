package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.idl.simulation.water.PotableWaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.water.PotableWaterProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.water.PotableWaterProducerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStore;
import com.traclabs.biosim.server.framework.BioModuleImpl;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class PotableWaterProducerDefinitionImpl extends
        StoreFlowRateControllableImpl implements
        PotableWaterProducerDefinitionOperations {
    private PotableWaterProducerDefinition myPotableWaterProducerDefinition;

    public PotableWaterProducerDefinitionImpl(BioModuleImpl pModule) {
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
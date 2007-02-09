package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.idl.simulation.water.WaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.water.WaterProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.water.WaterProducerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.water.WaterStore;
import com.traclabs.biosim.server.framework.BioModuleImpl;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class WaterProducerDefinitionImpl extends StoreFlowRateControllableImpl
        implements WaterProducerDefinitionOperations {
    private WaterProducerDefinition myWaterProducerDefinition;

    public WaterProducerDefinitionImpl(BioModuleImpl pModule) {
     super(pModule);    	WaterProducerDefinitionPOATie tie = new WaterProducerDefinitionPOATie(this);
    	myWaterProducerDefinition = tie._this(OrbUtils.getORB());
    }

    public WaterProducerDefinition getCorbaObject() {
        return myWaterProducerDefinition;
    }

    public void setWaterOutputs(WaterStore[] pStores, float[] pMaxFlowRates,
            float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
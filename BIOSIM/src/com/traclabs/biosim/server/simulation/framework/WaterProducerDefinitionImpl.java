package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.framework.WaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.WaterProducerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.framework.WaterProducerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.framework.WaterProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.water.WaterStore;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class WaterProducerDefinitionImpl extends StoreFlowRateControllableImpl implements WaterProducerDefinitionOperations {
    private WaterProducerDefinition myWaterProducerDefinition;
    
    public WaterProducerDefinitionImpl(){
        myWaterProducerDefinition = WaterProducerDefinitionHelper.narrow(OrbUtils.poaToCorbaObj(new WaterProducerDefinitionPOATie(this)));
    }
    
    public WaterProducerDefinition getCorbaObject(){
        return myWaterProducerDefinition;
    }
    public void setWaterOutputs(WaterStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.air.O2Store;
import com.traclabs.biosim.idl.simulation.framework.O2ProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.framework.O2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.O2ProducerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.framework.O2ProducerDefinitionPOATie;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class O2ProducerDefinitionImpl extends StoreFlowRateControllableImpl implements O2ProducerDefinitionOperations {
    private O2ProducerDefinition myO2ProducerDefinition;
    
    public O2ProducerDefinitionImpl(){
        myO2ProducerDefinition = O2ProducerDefinitionHelper.narrow(OrbUtils.poaToCorbaObj(new O2ProducerDefinitionPOATie(this)));
    }
    
    public O2ProducerDefinition getCorbaObject(){
        return myO2ProducerDefinition;
    }
    public void setO2Outputs(O2Store[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
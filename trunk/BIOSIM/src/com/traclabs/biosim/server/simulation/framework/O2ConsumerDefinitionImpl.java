package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.air.O2Store;
import com.traclabs.biosim.idl.simulation.framework.O2ConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.framework.O2ConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.O2ConsumerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.framework.O2ConsumerDefinitionPOATie;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class O2ConsumerDefinitionImpl extends StoreFlowRateControllableImpl implements O2ConsumerDefinitionOperations {
    private O2ConsumerDefinition myO2ConsumerDefinition;
    
    public O2ConsumerDefinitionImpl(){
        myO2ConsumerDefinition = O2ConsumerDefinitionHelper.narrow(OrbUtils.poaToCorbaObj(new O2ConsumerDefinitionPOATie(this)));
    }
    
    public O2ConsumerDefinition getCorbaObject(){
        return myO2ConsumerDefinition;
    }
    public void setO2Inputs(O2Store[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
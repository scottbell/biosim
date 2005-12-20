package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.idl.simulation.air.O2ConsumerDefinition;
import com.traclabs.biosim.idl.simulation.air.O2ConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.air.O2ConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.air.O2Store;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class O2ConsumerDefinitionImpl extends StoreFlowRateControllableImpl
        implements O2ConsumerDefinitionOperations {
    private O2ConsumerDefinition myO2ConsumerDefinition;

    public O2ConsumerDefinitionImpl() {

    	O2ConsumerDefinitionPOATie tie = new O2ConsumerDefinitionPOATie(this);
    	myO2ConsumerDefinition = tie._this(OrbUtils.getORB());
    }

    public O2ConsumerDefinition getCorbaObject() {
        return myO2ConsumerDefinition;
    }

    public void setO2Inputs(O2Store[] pStores, float[] pMaxFlowRates,
            float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
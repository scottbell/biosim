package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.simulation.air.O2ProducerDefinition;
import com.traclabs.biosim.server.simulation.air.O2ProducerDefinitionOperations;
import com.traclabs.biosim.server.simulation.air.O2ProducerDefinitionPOATie;
import com.traclabs.biosim.server.simulation.air.O2Store;
import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class O2ProducerDefinition extends StoreFlowRateControllable
        implements O2ProducerDefinitionOperations {
    private O2ProducerDefinition myO2ProducerDefinition;

    public O2ProducerDefinition(BioModule pModule) {
     super(pModule);    	O2ProducerDefinitionPOATie tie = new O2ProducerDefinitionPOATie(this);
    	myO2ProducerDefinition = tie._this(OrbUtils.getORB());
    }

    public O2ProducerDefinition getCorbaObject() {
        return myO2ProducerDefinition;
    }

    public void setO2Outputs(O2Store[] pStores, float[] pMaxFlowRates,
            float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
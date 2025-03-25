package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.simulation.air.H2ProducerDefinition;
import com.traclabs.biosim.server.simulation.air.H2ProducerDefinitionOperations;
import com.traclabs.biosim.server.simulation.air.H2ProducerDefinitionPOATie;
import com.traclabs.biosim.server.simulation.air.H2Store;
import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class H2ProducerDefinition extends StoreFlowRateControllable
        implements H2ProducerDefinitionOperations {
    private H2ProducerDefinition myH2ProducerDefinition;

    public H2ProducerDefinition(BioModule pModule) {
    	super(pModule);
    	H2ProducerDefinitionPOATie tie = new H2ProducerDefinitionPOATie(this);
    	myH2ProducerDefinition = tie._this(OrbUtils.getORB());
    }

    public H2ProducerDefinition getCorbaObject() {
        return myH2ProducerDefinition;
    }

    public void setH2Outputs(H2Store[] pStores, float[] pMaxFlowRates,
            float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
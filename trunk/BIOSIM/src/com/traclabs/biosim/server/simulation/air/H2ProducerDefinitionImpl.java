package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.idl.simulation.air.H2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.air.H2ProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.air.H2ProducerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.air.H2Store;
import com.traclabs.biosim.server.framework.BioModuleImpl;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class H2ProducerDefinitionImpl extends StoreFlowRateControllableImpl
        implements H2ProducerDefinitionOperations {
    private H2ProducerDefinition myH2ProducerDefinition;

    public H2ProducerDefinitionImpl(BioModuleImpl pModule) {
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
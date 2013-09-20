package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.idl.simulation.air.H2ConsumerDefinition;
import com.traclabs.biosim.idl.simulation.air.H2ConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.air.H2ConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.air.H2Store;
import com.traclabs.biosim.server.framework.BioModuleImpl;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class H2ConsumerDefinitionImpl extends StoreFlowRateControllableImpl
        implements H2ConsumerDefinitionOperations {
    private H2ConsumerDefinition myH2ConsumerDefinition;

    public H2ConsumerDefinitionImpl(BioModuleImpl pModule) {
super(pModule);

    	H2ConsumerDefinitionPOATie tie = new H2ConsumerDefinitionPOATie(this);
    	myH2ConsumerDefinition = tie._this(OrbUtils.getORB());
    }

    public H2ConsumerDefinition getCorbaObject() {
        return myH2ConsumerDefinition;
    }

    public void setH2Inputs(H2Store[] pStores, float[] pMaxFlowRates,
            float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
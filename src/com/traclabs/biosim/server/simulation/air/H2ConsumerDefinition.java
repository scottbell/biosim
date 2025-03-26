package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.air.H2ConsumerDefinitionOperations;
import com.traclabs.biosim.server.simulation.air.H2ConsumerDefinitionPOATie;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class H2ConsumerDefinition extends StoreFlowRateControllable
        implements H2ConsumerDefinitionOperations {
    private H2ConsumerDefinition myH2ConsumerDefinition;

    public H2ConsumerDefinition(BioModule pModule) {
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
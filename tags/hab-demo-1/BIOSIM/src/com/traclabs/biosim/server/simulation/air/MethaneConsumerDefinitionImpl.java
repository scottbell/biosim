package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.idl.simulation.air.MethaneConsumerDefinition;
import com.traclabs.biosim.idl.simulation.air.MethaneConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.air.MethaneConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.air.MethaneStore;
import com.traclabs.biosim.server.framework.BioModuleImpl;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class MethaneConsumerDefinitionImpl extends
        StoreFlowRateControllableImpl implements
        MethaneConsumerDefinitionOperations {
    private MethaneConsumerDefinition myMethaneConsumerDefinition;

    public MethaneConsumerDefinitionImpl(BioModuleImpl pModule) {
super(pModule);

    	MethaneConsumerDefinitionPOATie tie = new MethaneConsumerDefinitionPOATie(this);
    	myMethaneConsumerDefinition = tie._this(OrbUtils.getORB());
    }

    public MethaneConsumerDefinition getCorbaObject() {
        return myMethaneConsumerDefinition;
    }

    public void setMethaneInputs(MethaneStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
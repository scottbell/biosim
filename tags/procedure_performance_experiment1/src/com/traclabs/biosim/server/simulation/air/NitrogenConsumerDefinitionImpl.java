package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.idl.simulation.air.NitrogenConsumerDefinition;
import com.traclabs.biosim.idl.simulation.air.NitrogenConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.air.NitrogenConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.air.NitrogenStore;
import com.traclabs.biosim.server.framework.BioModuleImpl;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class NitrogenConsumerDefinitionImpl extends
        StoreFlowRateControllableImpl implements
        NitrogenConsumerDefinitionOperations {
    private NitrogenConsumerDefinition myNitrogenConsumerDefinition;

    public NitrogenConsumerDefinitionImpl(BioModuleImpl pModule) {
super(pModule);

    	NitrogenConsumerDefinitionPOATie tie = new NitrogenConsumerDefinitionPOATie(this);
    	myNitrogenConsumerDefinition = tie._this(OrbUtils.getORB());
    }

    public NitrogenConsumerDefinition getCorbaObject() {
        return myNitrogenConsumerDefinition;
    }

    public void setNitrogenInputs(NitrogenStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
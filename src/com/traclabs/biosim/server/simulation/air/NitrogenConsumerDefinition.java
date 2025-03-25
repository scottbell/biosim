package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.simulation.air.NitrogenConsumerDefinition;
import com.traclabs.biosim.server.simulation.air.NitrogenConsumerDefinitionOperations;
import com.traclabs.biosim.server.simulation.air.NitrogenConsumerDefinitionPOATie;
import com.traclabs.biosim.server.simulation.air.NitrogenStore;
import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class NitrogenConsumerDefinition extends
        StoreFlowRateControllable implements
        NitrogenConsumerDefinitionOperations {
    private NitrogenConsumerDefinition myNitrogenConsumerDefinition;

    public NitrogenConsumerDefinition(BioModule pModule) {
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
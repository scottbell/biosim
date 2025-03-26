package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.air.NitrogenProducerDefinitionOperations;
import com.traclabs.biosim.server.simulation.air.NitrogenProducerDefinitionPOATie;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class NitrogenProducerDefinition extends
        StoreFlowRateControllable implements
        NitrogenProducerDefinitionOperations {
    private NitrogenProducerDefinition myNitrogenProducerDefinition;

    public NitrogenProducerDefinition(BioModule pModule) {
     super(pModule);    	NitrogenProducerDefinitionPOATie tie = new NitrogenProducerDefinitionPOATie(this);
    	myNitrogenProducerDefinition = tie._this(OrbUtils.getORB());
    }

    public NitrogenProducerDefinition getCorbaObject() {
        return myNitrogenProducerDefinition;
    }

    public void setNitrogenOutputs(NitrogenStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.idl.simulation.air.NitrogenProducerDefinition;
import com.traclabs.biosim.idl.simulation.air.NitrogenProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.air.NitrogenProducerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.air.NitrogenStore;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class NitrogenProducerDefinitionImpl extends
        StoreFlowRateControllableImpl implements
        NitrogenProducerDefinitionOperations {
    private NitrogenProducerDefinition myNitrogenProducerDefinition;

    public NitrogenProducerDefinitionImpl() {

    	NitrogenProducerDefinitionPOATie tie = new NitrogenProducerDefinitionPOATie(this);
    	myNitrogenProducerDefinition = tie._this(OrbUtils.getORB());
    }

    public NitrogenProducerDefinition getCorbaObject() {
        return myNitrogenProducerDefinition;
    }

    public void setNitrogenOutputs(NitrogenStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
        setStores(pStores);
    }
}
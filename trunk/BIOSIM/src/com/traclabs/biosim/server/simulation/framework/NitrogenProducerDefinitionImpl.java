package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.air.NitrogenStore;
import com.traclabs.biosim.idl.simulation.framework.NitrogenProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.framework.NitrogenProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.NitrogenProducerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.framework.NitrogenProducerDefinitionPOATie;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class NitrogenProducerDefinitionImpl extends StoreFlowRateControllableImpl implements NitrogenProducerDefinitionOperations {
    private NitrogenProducerDefinition myNitrogenProducerDefinition;
    
    public NitrogenProducerDefinitionImpl(){
        myNitrogenProducerDefinition = NitrogenProducerDefinitionHelper.narrow(OrbUtils.poaToCorbaObj(new NitrogenProducerDefinitionPOATie(this)));
    }
    
    public NitrogenProducerDefinition getCorbaObject(){
        return myNitrogenProducerDefinition;
    }
    public void setNitrogenOutputs(NitrogenStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
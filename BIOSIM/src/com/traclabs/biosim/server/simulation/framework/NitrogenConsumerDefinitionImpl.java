package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.air.NitrogenStore;
import com.traclabs.biosim.idl.simulation.framework.NitrogenConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.framework.NitrogenConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.NitrogenConsumerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.framework.NitrogenConsumerDefinitionPOATie;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class NitrogenConsumerDefinitionImpl extends StoreFlowRateControllableImpl implements NitrogenConsumerDefinitionOperations {
    private NitrogenConsumerDefinition myNitrogenConsumerDefinition;
    
    public NitrogenConsumerDefinitionImpl(){
        myNitrogenConsumerDefinition = NitrogenConsumerDefinitionHelper.narrow(OrbUtils.poaToCorbaObj(new NitrogenConsumerDefinitionPOATie(this)));
    }
    
    public NitrogenConsumerDefinition getCorbaObject(){
        return myNitrogenConsumerDefinition;
    }
    public void setNitrogenInputs(NitrogenStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.framework.DryWasteConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.framework.DryWasteConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.DryWasteConsumerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.framework.DryWasteConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.waste.DryWasteStore;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class DryWasteConsumerDefinitionImpl extends StoreFlowRateControllableImpl implements DryWasteConsumerDefinitionOperations {
    private DryWasteConsumerDefinition myDryWasteConsumerDefinition;
    
    public DryWasteConsumerDefinitionImpl(){
        myDryWasteConsumerDefinition = DryWasteConsumerDefinitionHelper.narrow(OrbUtils.poaToCorbaObj(new DryWasteConsumerDefinitionPOATie(this)));
    }
    
    public DryWasteConsumerDefinition getCorbaObject(){
        return myDryWasteConsumerDefinition;
    }
    public void setDryWasteInputs(DryWasteStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
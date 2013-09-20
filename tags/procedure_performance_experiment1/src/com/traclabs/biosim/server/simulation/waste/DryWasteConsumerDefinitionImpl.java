package com.traclabs.biosim.server.simulation.waste;

import com.traclabs.biosim.idl.simulation.waste.DryWasteConsumerDefinition;
import com.traclabs.biosim.idl.simulation.waste.DryWasteConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.waste.DryWasteConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.waste.DryWasteStore;
import com.traclabs.biosim.server.framework.BioModuleImpl;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class DryWasteConsumerDefinitionImpl extends
        StoreFlowRateControllableImpl implements
        DryWasteConsumerDefinitionOperations {
    private DryWasteConsumerDefinition myDryWasteConsumerDefinition;

    public DryWasteConsumerDefinitionImpl(BioModuleImpl pModule) {
super(pModule);

    	DryWasteConsumerDefinitionPOATie tie = new DryWasteConsumerDefinitionPOATie(this);
    	myDryWasteConsumerDefinition = tie._this(OrbUtils.getORB());
    }

    public DryWasteConsumerDefinition getCorbaObject() {
        return myDryWasteConsumerDefinition;
    }

    public void setDryWasteInputs(DryWasteStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
package com.traclabs.biosim.server.simulation.waste;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;
import com.traclabs.biosim.server.simulation.waste.DryWasteConsumerDefinitionOperations;
import com.traclabs.biosim.server.simulation.waste.DryWasteConsumerDefinitionPOATie;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class DryWasteConsumerDefinition extends
        StoreFlowRateControllable implements
        DryWasteConsumerDefinitionOperations {
    private DryWasteConsumerDefinition myDryWasteConsumerDefinition;

    public DryWasteConsumerDefinition(BioModule pModule) {
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
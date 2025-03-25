package com.traclabs.biosim.server.simulation.waste;

import com.traclabs.biosim.server.simulation.waste.DryWasteProducerDefinition;
import com.traclabs.biosim.server.simulation.waste.DryWasteProducerDefinitionOperations;
import com.traclabs.biosim.server.simulation.waste.DryWasteProducerDefinitionPOATie;
import com.traclabs.biosim.server.simulation.waste.DryWasteStore;
import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class DryWasteProducerDefinition extends
        StoreFlowRateControllable implements
        DryWasteProducerDefinitionOperations {
    private DryWasteProducerDefinition myDryWasteProducerDefinition;

    public DryWasteProducerDefinition(BioModule pModule) {
     super(pModule);    	DryWasteProducerDefinitionPOATie tie = new DryWasteProducerDefinitionPOATie(this);
    	myDryWasteProducerDefinition = tie._this(OrbUtils.getORB());
    }

    public DryWasteProducerDefinition getCorbaObject() {
        return myDryWasteProducerDefinition;
    }

    public void setDryWasteOutputs(DryWasteStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
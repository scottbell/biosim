package com.traclabs.biosim.server.simulation.waste;

import com.traclabs.biosim.idl.simulation.waste.DryWasteProducerDefinition;
import com.traclabs.biosim.idl.simulation.waste.DryWasteProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.waste.DryWasteProducerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.waste.DryWasteStore;
import com.traclabs.biosim.server.framework.BioModuleImpl;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class DryWasteProducerDefinitionImpl extends
        StoreFlowRateControllableImpl implements
        DryWasteProducerDefinitionOperations {
    private DryWasteProducerDefinition myDryWasteProducerDefinition;

    public DryWasteProducerDefinitionImpl(BioModuleImpl pModule) {
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
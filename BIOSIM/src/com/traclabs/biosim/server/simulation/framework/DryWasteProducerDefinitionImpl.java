package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.framework.DryWasteProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.DryWasteProducerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.framework.DryWasteProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.framework.DryWasteProducerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.waste.DryWasteStore;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class DryWasteProducerDefinitionImpl extends
        StoreFlowRateControllableImpl implements
        DryWasteProducerDefinitionOperations {
    private DryWasteProducerDefinition myDryWasteProducerDefinition;

    public DryWasteProducerDefinitionImpl() {
        myDryWasteProducerDefinition = DryWasteProducerDefinitionHelper
                .narrow(OrbUtils
                        .poaToCorbaObj(new DryWasteProducerDefinitionPOATie(
                                this)));
    }

    public DryWasteProducerDefinition getCorbaObject() {
        return myDryWasteProducerDefinition;
    }

    public void setDryWasteOutputs(DryWasteStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
package com.traclabs.biosim.server.simulation.waste;

import com.traclabs.biosim.idl.simulation.waste.DryWasteProducerDefinition;
import com.traclabs.biosim.idl.simulation.waste.DryWasteProducerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.waste.DryWasteProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.waste.DryWasteProducerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.waste.DryWasteStore;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
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
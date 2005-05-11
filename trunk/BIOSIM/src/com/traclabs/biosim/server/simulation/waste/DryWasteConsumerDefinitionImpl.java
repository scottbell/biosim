package com.traclabs.biosim.server.simulation.waste;

import com.traclabs.biosim.idl.simulation.waste.DryWasteConsumerDefinition;
import com.traclabs.biosim.idl.simulation.waste.DryWasteConsumerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.waste.DryWasteConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.waste.DryWasteConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.waste.DryWasteStore;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class DryWasteConsumerDefinitionImpl extends
        StoreFlowRateControllableImpl implements
        DryWasteConsumerDefinitionOperations {
    private DryWasteConsumerDefinition myDryWasteConsumerDefinition;

    public DryWasteConsumerDefinitionImpl() {
        myDryWasteConsumerDefinition = DryWasteConsumerDefinitionHelper
                .narrow(OrbUtils
                        .poaToCorbaObj(new DryWasteConsumerDefinitionPOATie(
                                this)));
    }

    public DryWasteConsumerDefinition getCorbaObject() {
        return myDryWasteConsumerDefinition;
    }

    public void setDryWasteInputs(DryWasteStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
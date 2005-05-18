package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.idl.simulation.air.MethaneConsumerDefinition;
import com.traclabs.biosim.idl.simulation.air.MethaneConsumerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.air.MethaneConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.air.MethaneConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.air.MethaneStore;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class MethaneConsumerDefinitionImpl extends
        StoreFlowRateControllableImpl implements
        MethaneConsumerDefinitionOperations {
    private MethaneConsumerDefinition myMethaneConsumerDefinition;

    public MethaneConsumerDefinitionImpl() {
        myMethaneConsumerDefinition = MethaneConsumerDefinitionHelper
                .narrow(OrbUtils
                        .poaToCorbaObj(new MethaneConsumerDefinitionPOATie(
                                this)));
    }

    public MethaneConsumerDefinition getCorbaObject() {
        return myMethaneConsumerDefinition;
    }

    public void setMethaneInputs(MethaneStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
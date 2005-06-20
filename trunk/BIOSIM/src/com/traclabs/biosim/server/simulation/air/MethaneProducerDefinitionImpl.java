package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.idl.simulation.air.MethaneProducerDefinition;
import com.traclabs.biosim.idl.simulation.air.MethaneProducerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.air.MethaneProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.air.MethaneProducerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.air.MethaneStore;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class MethaneProducerDefinitionImpl extends
        StoreFlowRateControllableImpl implements
        MethaneProducerDefinitionOperations {
    private MethaneProducerDefinition myMethaneProducerDefinition;

    public MethaneProducerDefinitionImpl() {
        myMethaneProducerDefinition = MethaneProducerDefinitionHelper
                .narrow(OrbUtils
                        .poaToCorbaObj(new MethaneProducerDefinitionPOATie(
                                this)));
    }

    public MethaneProducerDefinition getCorbaObject() {
        return myMethaneProducerDefinition;
    }

    public void setMethaneOutputs(MethaneStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
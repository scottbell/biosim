package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.air.H2Store;
import com.traclabs.biosim.idl.simulation.framework.H2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.H2ProducerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.framework.H2ProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.framework.H2ProducerDefinitionPOATie;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class H2ProducerDefinitionImpl extends StoreFlowRateControllableImpl
        implements H2ProducerDefinitionOperations {
    private H2ProducerDefinition myH2ProducerDefinition;

    public H2ProducerDefinitionImpl() {
        myH2ProducerDefinition = H2ProducerDefinitionHelper.narrow(OrbUtils
                .poaToCorbaObj(new H2ProducerDefinitionPOATie(this)));
    }

    public H2ProducerDefinition getCorbaObject() {
        return myH2ProducerDefinition;
    }

    public void setH2Outputs(H2Store[] pStores, float[] pMaxFlowRates,
            float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
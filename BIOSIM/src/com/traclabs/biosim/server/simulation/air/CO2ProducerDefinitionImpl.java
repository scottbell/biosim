package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.idl.simulation.air.CO2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.air.CO2ProducerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.air.CO2ProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.air.CO2ProducerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.air.CO2Store;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class CO2ProducerDefinitionImpl extends StoreFlowRateControllableImpl
        implements CO2ProducerDefinitionOperations {
    private CO2ProducerDefinition myCO2ProducerDefinition;

    public CO2ProducerDefinitionImpl() {
        myCO2ProducerDefinition = CO2ProducerDefinitionHelper.narrow(OrbUtils
                .poaToCorbaObj(new CO2ProducerDefinitionPOATie(this)));
    }

    public CO2ProducerDefinition getCorbaObject() {
        return myCO2ProducerDefinition;
    }

    public void setCO2Outputs(CO2Store[] pStores, float[] pMaxFlowRates,
            float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.air.CO2ProducerDefinitionOperations;
import com.traclabs.biosim.server.simulation.air.CO2ProducerDefinitionPOATie;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class CO2ProducerDefinition extends StoreFlowRateControllable
        implements CO2ProducerDefinitionOperations {
    private CO2ProducerDefinition myCO2ProducerDefinition;

    public CO2ProducerDefinition(BioModule pModule) {
    	super(pModule);
    	CO2ProducerDefinitionPOATie tie = new CO2ProducerDefinitionPOATie(this);
    	myCO2ProducerDefinition = tie._this(OrbUtils.getORB());
    }

    public CO2ProducerDefinition getCorbaObject() {
        return myCO2ProducerDefinition;
    }

    public void setCO2Outputs(CO2Store[] pStores, float[] pMaxFlowRates,
            float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
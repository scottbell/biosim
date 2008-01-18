package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.idl.simulation.air.CO2ConsumerDefinition;
import com.traclabs.biosim.idl.simulation.air.CO2ConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.air.CO2ConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.air.CO2Store;
import com.traclabs.biosim.server.framework.BioModuleImpl;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class CO2ConsumerDefinitionImpl extends StoreFlowRateControllableImpl
        implements CO2ConsumerDefinitionOperations {
    private CO2ConsumerDefinition myCO2ConsumerDefinition;

    public CO2ConsumerDefinitionImpl(BioModuleImpl pModule) {
super(pModule);

    	CO2ConsumerDefinitionPOATie tie = new CO2ConsumerDefinitionPOATie(this);
    	myCO2ConsumerDefinition = tie._this(OrbUtils.getORB());
    }

    public CO2ConsumerDefinition getCorbaObject() {
        return myCO2ConsumerDefinition;
    }

    public void setCO2Inputs(CO2Store[] pStores, float[] pMaxFlowRates,
            float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
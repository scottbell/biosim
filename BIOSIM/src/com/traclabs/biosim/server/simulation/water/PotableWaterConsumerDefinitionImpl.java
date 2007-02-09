package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.idl.simulation.water.PotableWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.water.PotableWaterConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.water.PotableWaterConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStore;
import com.traclabs.biosim.server.framework.BioModuleImpl;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class PotableWaterConsumerDefinitionImpl extends
        StoreFlowRateControllableImpl implements
        PotableWaterConsumerDefinitionOperations {
    private PotableWaterConsumerDefinition myPotableWaterConsumerDefinition;

    public PotableWaterConsumerDefinitionImpl(BioModuleImpl pModule) {
super(pModule);

    	PotableWaterConsumerDefinitionPOATie tie = new PotableWaterConsumerDefinitionPOATie(this);
    	myPotableWaterConsumerDefinition = tie._this(OrbUtils.getORB());
    }

    public PotableWaterConsumerDefinition getCorbaObject() {
        return myPotableWaterConsumerDefinition;
    }

    public void setPotableWaterInputs(PotableWaterStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
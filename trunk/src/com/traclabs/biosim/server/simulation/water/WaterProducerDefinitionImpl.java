package com.traclabs.biosim.server.simulation.water;

import java.util.Arrays;

import com.traclabs.biosim.idl.simulation.water.WaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.water.WaterProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.water.WaterProducerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.water.WaterStore;
import com.traclabs.biosim.idl.simulation.water.WaterStoreHelper;
import com.traclabs.biosim.server.framework.BioModuleImpl;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class WaterProducerDefinitionImpl extends StoreFlowRateControllableImpl
        implements WaterProducerDefinitionOperations {
    private WaterProducerDefinition myWaterProducerDefinition;

	public float pushResourceToStores(float amountToPush, float temperatureOfWater) {
		if (getStores() == null)
            return 0f;
        if (amountToPush <= 0){
        	Arrays.fill(getActualFlowRates(), 0f);
        	return 0f;
        }
        float resourceRemaining = amountToPush;
        for (int i = 0; (i < getStores().length) && (resourceRemaining > 0); i++) {
            float resourceToDistributeFirst = Math.min(resourceRemaining,
                    getMaxFlowRate(i));
            float resourceToDistributeFinal = Math.min(
                    resourceToDistributeFirst, getDesiredFlowRate(i));
            WaterStore waterStore = WaterStoreHelper.narrow(getStores()[i]);
            waterStore.addWaterWithTemperature(resourceToDistributeFinal, temperatureOfWater);
            getActualFlowRates()[i] = resourceToDistributeFinal;
            resourceRemaining -= getActualFlowRate(i);
        }
        return (amountToPush - resourceRemaining);
	}

	public WaterProducerDefinitionImpl(BioModuleImpl pModule) {
     super(pModule);    	WaterProducerDefinitionPOATie tie = new WaterProducerDefinitionPOATie(this);
    	myWaterProducerDefinition = tie._this(OrbUtils.getORB());
    }

    public WaterProducerDefinition getCorbaObject() {
        return myWaterProducerDefinition;
    }

    public void setWaterOutputs(WaterStore[] pStores, float[] pMaxFlowRates,
            float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
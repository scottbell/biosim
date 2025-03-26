package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;

import java.util.Arrays;

/**
 * @author Scott Bell
 */

public class WaterProducerDefinition extends StoreFlowRateControllable {
    public WaterProducerDefinition(BioModule pModule) {
        super(pModule);
    }
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
            WaterStore waterStore = (WaterStore)(getStores()[i]);
            waterStore.addWaterWithTemperature(resourceToDistributeFinal, temperatureOfWater);
            getActualFlowRates()[i] = resourceToDistributeFinal;
            resourceRemaining -= getActualFlowRate(i);
        }
        return (amountToPush - resourceRemaining);
	}

    public void setWaterOutputs(WaterStore[] pStores, float[] pMaxFlowRates,
            float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}
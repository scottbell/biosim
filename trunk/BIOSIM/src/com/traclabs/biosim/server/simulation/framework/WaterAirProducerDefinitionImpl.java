package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.WaterAirProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.water.WaterStore;

/**
 * @author Scott Bell
 */

public class WaterAirProducerDefinitionImpl extends StoreEnvironmentFlowRateControllableImpl implements WaterAirProducerDefinitionOperations {
    
    public void setWaterAirEnvironmentOutputs(SimEnvironment[] pEnvironments, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setEnvironments(pEnvironments);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }

    public void setWaterAirStoreOutputs(WaterStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }

}
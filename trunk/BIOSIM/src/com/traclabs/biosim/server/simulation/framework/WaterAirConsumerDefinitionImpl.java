package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.WaterAirConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.water.WaterStore;

/**
 * @author Scott Bell
 */

public class WaterAirConsumerDefinitionImpl extends StoreEnvironmentFlowRateControllableImpl implements WaterAirConsumerDefinitionOperations {
    
    public void setWaterAirEnvironmentInputs(SimEnvironment[] pEnvironments, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setEnvironments(pEnvironments);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }

    public void setWaterAirStoreInputs(WaterStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }

}
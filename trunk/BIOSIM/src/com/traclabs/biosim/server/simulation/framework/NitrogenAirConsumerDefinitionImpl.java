package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.air.NitrogenStore;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.NitrogenAirConsumerDefinitionOperations;

/**
 * @author Scott Bell
 */

public class NitrogenAirConsumerDefinitionImpl extends StoreEnvironmentFlowRateControllableImpl implements NitrogenAirConsumerDefinitionOperations {
    
    public void setNitrogenAirEnvironmentInputs(SimEnvironment[] pEnvironments, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setEnvironments(pEnvironments);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }

    public void setNitrogenAirStoreInputs(NitrogenStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }

}
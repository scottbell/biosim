package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.AirConsumerDefinitionOperations;

/**
 * @author Scott Bell
 */

public class AirConsumerDefinitionImpl extends EnvironmentFlowRateControllableImpl implements AirConsumerDefinitionOperations {
    public void setAirInputs(SimEnvironment[] pSimEnvironments, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setEnvironments(pSimEnvironments);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
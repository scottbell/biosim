package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.AirProducerDefinitionOperations;

/**
 * @author Scott Bell
 */

public class AirProducerDefinitionImpl extends EnvironmentFlowRateControllableImpl implements AirProducerDefinitionOperations {
    public void setAirOutputs(SimEnvironment[] pSimEnvironments, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setEnvironments(pSimEnvironments);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.LightConsumerDefinitionOperations;

/**
 * @author Scott Bell
 */

public class LightConsumerDefinitionImpl extends EnvironmentFlowRateControllableImpl implements LightConsumerDefinitionOperations {
    public void setLightInput(SimEnvironment pSimEnvironment) {
        SimEnvironment[] singleArray = new SimEnvironment[1];
        singleArray[0] = pSimEnvironment;
        setEnvironments(singleArray);
    }
}
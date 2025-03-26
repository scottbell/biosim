package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.server.framework.BioModule;

/**
 * @author Scott Bell
 */

public class LightConsumerDefinition extends EnvironmentFlowRateControllable {
    public LightConsumerDefinition(BioModule pModule) {
    	super(pModule);
    }

    public void setLightInput(SimEnvironment pSimEnvironment) {
        SimEnvironment[] singleArray = new SimEnvironment[1];
        singleArray[0] = pSimEnvironment;
        setInitialEnvironments(singleArray);
    }
}
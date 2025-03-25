package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.server.simulation.environment.LightConsumerDefinition;
import com.traclabs.biosim.server.simulation.environment.LightConsumerDefinitionOperations;
import com.traclabs.biosim.server.simulation.environment.LightConsumerDefinitionPOATie;
import com.traclabs.biosim.server.simulation.environment.SimEnvironment;
import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class LightConsumerDefinition extends
        EnvironmentFlowRateControllable implements
        LightConsumerDefinitionOperations {
    private LightConsumerDefinition myLightConsumerDefinition;

    public LightConsumerDefinition(BioModule pModule) {
    	super(pModule);
    	LightConsumerDefinitionPOATie tie = new LightConsumerDefinitionPOATie(this);
    	myLightConsumerDefinition = tie._this(OrbUtils.getORB());
    }

    public LightConsumerDefinition getCorbaObject() {
        return myLightConsumerDefinition;
    }

    public void setLightInput(SimEnvironment pSimEnvironment) {
        SimEnvironment[] singleArray = new SimEnvironment[1];
        singleArray[0] = pSimEnvironment;
        setInitialEnvironments(singleArray);
    }
}
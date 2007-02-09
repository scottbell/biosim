package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.idl.simulation.environment.LightConsumerDefinition;
import com.traclabs.biosim.idl.simulation.environment.LightConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.environment.LightConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.server.framework.BioModuleImpl;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class LightConsumerDefinitionImpl extends
        EnvironmentFlowRateControllableImpl implements
        LightConsumerDefinitionOperations {
    private LightConsumerDefinition myLightConsumerDefinition;

    public LightConsumerDefinitionImpl(BioModuleImpl pModule) {
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
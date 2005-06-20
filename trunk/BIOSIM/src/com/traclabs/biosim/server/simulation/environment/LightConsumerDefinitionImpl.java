package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.idl.simulation.environment.LightConsumerDefinition;
import com.traclabs.biosim.idl.simulation.environment.LightConsumerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.environment.LightConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.environment.LightConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class LightConsumerDefinitionImpl extends
        EnvironmentFlowRateControllableImpl implements
        LightConsumerDefinitionOperations {
    private LightConsumerDefinition myLightConsumerDefinition;

    public LightConsumerDefinitionImpl() {
        myLightConsumerDefinition = LightConsumerDefinitionHelper
                .narrow(OrbUtils
                        .poaToCorbaObj(new LightConsumerDefinitionPOATie(this)));
    }

    public LightConsumerDefinition getCorbaObject() {
        return myLightConsumerDefinition;
    }

    public void setLightInput(SimEnvironment pSimEnvironment) {
        SimEnvironment[] singleArray = new SimEnvironment[1];
        singleArray[0] = pSimEnvironment;
        setEnvironments(singleArray);
    }
}
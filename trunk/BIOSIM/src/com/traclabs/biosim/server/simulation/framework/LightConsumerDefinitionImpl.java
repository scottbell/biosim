package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.LightConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.framework.LightConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.LightConsumerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.framework.LightConsumerDefinitionPOATie;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class LightConsumerDefinitionImpl extends EnvironmentFlowRateControllableImpl implements LightConsumerDefinitionOperations {
    private LightConsumerDefinition myLightConsumerDefinition;
    
    public LightConsumerDefinitionImpl(){
        myLightConsumerDefinition = LightConsumerDefinitionHelper.narrow(OrbUtils.poaToCorbaObj(new LightConsumerDefinitionPOATie(this)));
    }
    
    public LightConsumerDefinition getCorbaObject(){
        return myLightConsumerDefinition;
    }
    public void setLightInput(SimEnvironment pSimEnvironment) {
        SimEnvironment[] singleArray = new SimEnvironment[1];
        singleArray[0] = pSimEnvironment;
        setEnvironments(singleArray);
    }
}
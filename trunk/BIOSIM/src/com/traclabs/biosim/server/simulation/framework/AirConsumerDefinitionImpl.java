package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.AirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.AirConsumerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.framework.AirConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.framework.AirConsumerDefinitionPOATie;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class AirConsumerDefinitionImpl extends
        EnvironmentFlowRateControllableImpl implements
        AirConsumerDefinitionOperations {
    private AirConsumerDefinition myAirConsumerDefinition;

    public AirConsumerDefinitionImpl() {
        myAirConsumerDefinition = AirConsumerDefinitionHelper.narrow(OrbUtils
                .poaToCorbaObj(new AirConsumerDefinitionPOATie(this)));
    }

    public AirConsumerDefinition getCorbaObject() {
        return myAirConsumerDefinition;
    }

    public void setAirInputs(SimEnvironment[] pSimEnvironments,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setEnvironments(pSimEnvironments);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
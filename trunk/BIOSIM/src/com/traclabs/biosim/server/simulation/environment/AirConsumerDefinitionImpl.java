package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.idl.simulation.environment.AirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.util.OrbUtils;

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
package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.idl.simulation.environment.AirProducerDefinition;
import com.traclabs.biosim.idl.simulation.environment.AirProducerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.environment.AirProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.environment.AirProducerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class AirProducerDefinitionImpl extends
        EnvironmentFlowRateControllableImpl implements
        AirProducerDefinitionOperations {
    private AirProducerDefinition myAirProducerDefinition;

    public AirProducerDefinitionImpl() {
        myAirProducerDefinition = AirProducerDefinitionHelper.narrow(OrbUtils
                .poaToCorbaObj(new AirProducerDefinitionPOATie(this)));
    }

    public AirProducerDefinition getCorbaObject() {
        return myAirProducerDefinition;
    }

    public void setAirOutputs(SimEnvironment[] pSimEnvironments,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setEnvironments(pSimEnvironments);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}
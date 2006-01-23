package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.idl.simulation.environment.AirProducerDefinition;
import com.traclabs.biosim.idl.simulation.environment.AirProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.environment.AirProducerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class AirProducerDefinitionImpl extends
        EnvironmentFlowRateControllableImpl implements
        AirProducerDefinitionOperations {
    private AirProducerDefinition myAirProducerDefinition;

    public AirProducerDefinitionImpl() {

    	AirProducerDefinitionPOATie tie = new AirProducerDefinitionPOATie(this);
    	myAirProducerDefinition = tie._this(OrbUtils.getORB());
    }

    public AirProducerDefinition getCorbaObject() {
        return myAirProducerDefinition;
    }

    public void setAirOutputs(SimEnvironment[] pSimEnvironments,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
    	setInitialEnvironments(pSimEnvironments);
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
    }
}
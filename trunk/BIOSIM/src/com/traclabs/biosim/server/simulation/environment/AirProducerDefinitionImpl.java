package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.idl.simulation.environment.Air;
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
	
	public void pushAirToEnvironment(Air airToPush, int indexOfEnvironment){
		float actualFlowrateToEnvironment = 0f;
		SimEnvironment environment = getEnvironments()[indexOfEnvironment];
		actualFlowrateToEnvironment += environment.getO2Store().add(airToPush.o2Moles);
		actualFlowrateToEnvironment += environment.getCO2Store().add(airToPush.co2Moles);
		actualFlowrateToEnvironment += environment.getOtherStore().add(airToPush.otherMoles);
		actualFlowrateToEnvironment += environment.getVaporStore().add(airToPush.vaporMoles);
		actualFlowrateToEnvironment += environment.getNitrogenStore().add(airToPush.nitrogenMoles);
		setActualFlowRate(actualFlowrateToEnvironment, indexOfEnvironment);
	}
	
	
	private static float calculateTotalAirMoles(Air air){
		return air.o2Moles + air.co2Moles + air.nitrogenMoles + air.otherMoles + air.vaporMoles;
	}
}
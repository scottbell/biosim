package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.idl.simulation.environment.Air;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.Store;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class AirConsumerDefinitionImpl extends
        EnvironmentFlowRateControllableImpl implements
        AirConsumerDefinitionOperations {
    private AirConsumerDefinition myAirConsumerDefinition;

    public AirConsumerDefinitionImpl() {

    	AirConsumerDefinitionPOATie tie = new AirConsumerDefinitionPOATie(this);
    	myAirConsumerDefinition = tie._this(OrbUtils.getORB());
    }

    public AirConsumerDefinition getCorbaObject() {
        return myAirConsumerDefinition;
    }

    public void setAirInputs(SimEnvironment[] pSimEnvironments,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setInitialEnvironments(pSimEnvironments);
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
    }

	public Air getMostAirFromEnvironments() {
		Air airToReturn = new Air();
		for (int i = 0; i < getEnvironments().length; i++){
			SimEnvironment environment = getEnvironments()[i];
			float airMolesToTake = Math.min(getMaxFlowRate(i), getDesiredFlowRate(i));
			float molesTakenForThisFlowrate = 0f;
			molesTakenForThisFlowrate += airToReturn.o2Moles += takeGasFromAir(airMolesToTake, environment.getO2Store(), environment);
			molesTakenForThisFlowrate += airToReturn.co2Moles += takeGasFromAir(airMolesToTake, environment.getCO2Store(), environment);
			molesTakenForThisFlowrate += airToReturn.otherMoles += takeGasFromAir(airMolesToTake, environment.getOtherStore(), environment);
			molesTakenForThisFlowrate += airToReturn.vaporMoles += takeGasFromAir(airMolesToTake, environment.getVaporStore(), environment);
			molesTakenForThisFlowrate += airToReturn.nitrogenMoles += takeGasFromAir(airMolesToTake, environment.getNitrogenStore(), environment);
			setActualFlowRate(molesTakenForThisFlowrate, i);
		}
		return airToReturn;
	}
	
	private static float calculateTotalAirMoles(Air air){
		return air.o2Moles + air.co2Moles + air.nitrogenMoles + air.otherMoles + air.vaporMoles;
	}

	private static float takeGasFromAir(float airMolesToTake, Store store, SimEnvironment environment) {
		if (environment.getTotalMoles() == 0)
			return 0f;
		float gasPercentage = store.getCurrentLevel() / environment.getTotalMoles();
		float gasMolesToTake = airMolesToTake * gasPercentage;
		return store.take(gasMolesToTake);
	}
}
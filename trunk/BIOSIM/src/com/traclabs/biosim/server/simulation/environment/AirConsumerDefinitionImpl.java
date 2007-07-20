package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.idl.simulation.environment.Air;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.Store;
import com.traclabs.biosim.server.framework.BioModuleImpl;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class AirConsumerDefinitionImpl extends
        EnvironmentFlowRateControllableImpl implements
        AirConsumerDefinitionOperations {
    private AirConsumerDefinition myAirConsumerDefinition;

    public AirConsumerDefinitionImpl(BioModuleImpl pModule) {
    	super(pModule);
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
    

	
	public Air getAirFromEnvironment(float molesOfAir, int indexOfEnvironment){
		if (getEnvironments().length < indexOfEnvironment)
			return new Air();
		float actualFlowrateToEnvironment = 0f;
		SimEnvironment environment = getEnvironments()[indexOfEnvironment];
		float o2Percentage = environment.getO2Store().getCurrentLevel();
		/*
		actualFlowrateToEnvironment += environment.getO2Store().take(randomFilter(airToPush.o2Moles));
		actualFlowrateToEnvironment += environment.getCO2Store().take(randomFilter(airToPush.co2Moles));
		actualFlowrateToEnvironment += environment.getOtherStore().take(randomFilter(airToPush.otherMoles));
		actualFlowrateToEnvironment += environment.getVaporStore().take(randomFilter(airToPush.vaporMoles));
		actualFlowrateToEnvironment += environment.getNitrogenStore().take(randomFilter(airToPush.nitrogenMoles));
		*/
		setActualFlowRate(actualFlowrateToEnvironment, indexOfEnvironment);
		return new Air();
	}

	public Air getMostAirFromEnvironments() {
		Air airToReturn = new Air();
		for (int i = 0; i < getEnvironments().length; i++){
			SimEnvironment environment = getEnvironments()[i];
			float airMolesToTake = Math.min(getMaxFlowRate(i), getDesiredFlowRate(i));
			airMolesToTake = randomFilter(airMolesToTake);
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
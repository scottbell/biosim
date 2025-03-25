package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.server.simulation.environment.EnvironmentCO2StoreOperations;

public class EnvironmentCO2Store extends EnvironmentStore implements EnvironmentCO2StoreOperations{

	public EnvironmentCO2Store(SimEnvironment pSimEnvironment) {
		super(pSimEnvironment, "CO2");
	}
	
	public void tick(){
		super.tick();
	}

}

package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.server.simulation.environment.EnvironmentO2StoreOperations;

public class EnvironmentO2Store extends EnvironmentStore implements EnvironmentO2StoreOperations{

	public EnvironmentO2Store(SimEnvironment pSimEnvironment) {
		super(pSimEnvironment, "O2");
	}
	
	public float take(float pAmount){
    	return super.take(pAmount);
    }
	
}

package com.traclabs.biosim.server.simulation.environment;


public class EnvironmentO2Store extends EnvironmentStore{

	public EnvironmentO2Store(SimEnvironment pSimEnvironment) {
		super(pSimEnvironment, "O2");
	}
	
	public float take(float pAmount){
    	return super.take(pAmount);
    }
	
}

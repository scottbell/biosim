package com.traclabs.biosim.server.simulation.environment;


import com.traclabs.biosim.server.simulation.air.IO2Store;

public class EnvironmentO2Store extends EnvironmentStore implements IO2Store {

	public EnvironmentO2Store(SimEnvironment pSimEnvironment) {
		super(pSimEnvironment, "O2");
	}
	
	public float take(float pAmount){
    	return super.take(pAmount);
    }
	
}

package com.traclabs.biosim.server.simulation.environment;


import com.traclabs.biosim.server.simulation.air.ICO2Store;

public class EnvironmentCO2Store extends EnvironmentStore implements ICO2Store {

	public EnvironmentCO2Store(SimEnvironment pSimEnvironment) {
		super(pSimEnvironment, "CO2");
	}
	
	public void tick(){
		super.tick();
	}

}

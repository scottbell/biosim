package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.idl.simulation.environment.EnvironmentCO2StoreOperations;

public class EnvironmentCO2StoreImpl extends EnvironmentStoreImpl implements EnvironmentCO2StoreOperations{

	public EnvironmentCO2StoreImpl(SimEnvironmentImpl pSimEnvironmentImpl) {
		super(pSimEnvironmentImpl, "CO2");
	}
	
	public void tick(){
		super.tick();
	}

}

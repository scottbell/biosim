package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.idl.simulation.environment.EnvironmentO2StoreOperations;

public class EnvironmentO2StoreImpl extends EnvironmentStoreImpl implements EnvironmentO2StoreOperations{

	public EnvironmentO2StoreImpl(SimEnvironmentImpl pSimEnvironmentImpl) {
		super(pSimEnvironmentImpl, "O2");
	}
	
	public float take(float pAmount){
    	return super.take(pAmount);
    }
	
}

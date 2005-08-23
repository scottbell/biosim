package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.idl.simulation.environment.EnvironmentO2StoreOperations;

public class EnvironmentO2StoreImpl extends EnvironmentStoreImpl implements EnvironmentO2StoreOperations{

	public EnvironmentO2StoreImpl(SimEnvironmentImpl pSimEnvironmentImpl) {
		super(pSimEnvironmentImpl, "O2");
	}
	
	public float take(float amountRequested) {
		float result = super.take(amountRequested);
		myLogger.debug("taking "+amountRequested + " current level ="+getNonCachedLevel());
        return result;
    }
	
	public float add(float amountRequested) {
		float result = super.add(amountRequested);
		myLogger.debug("adding "+amountRequested + " current level ="+getNonCachedLevel());
        return result;
    }
	
	public void tick(){
		super.tick();
	}
}

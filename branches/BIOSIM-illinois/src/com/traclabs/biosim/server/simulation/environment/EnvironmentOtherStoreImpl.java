package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.idl.simulation.environment.EnvironmentOtherStoreOperations;

public class EnvironmentOtherStoreImpl extends EnvironmentStoreImpl implements EnvironmentOtherStoreOperations{

	public EnvironmentOtherStoreImpl(SimEnvironmentImpl pSimEnvironmentImpl) {
		super(pSimEnvironmentImpl, "Other");
	}

}

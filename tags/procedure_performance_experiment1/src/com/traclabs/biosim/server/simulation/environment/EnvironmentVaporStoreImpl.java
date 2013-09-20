package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.idl.simulation.environment.EnvironmentVaporStoreOperations;

public class EnvironmentVaporStoreImpl extends EnvironmentStoreImpl implements EnvironmentVaporStoreOperations{

	public EnvironmentVaporStoreImpl(SimEnvironmentImpl pSimEnvironmentImpl) {
		super(pSimEnvironmentImpl, "Vapor");
	}

}

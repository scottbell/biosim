package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.server.simulation.environment.EnvironmentVaporStoreOperations;

public class EnvironmentVaporStore extends EnvironmentStore implements EnvironmentVaporStoreOperations{

	public EnvironmentVaporStore(SimEnvironment pSimEnvironment) {
		super(pSimEnvironment, "Vapor");
	}

}

package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.server.simulation.environment.EnvironmentOtherStoreOperations;

public class EnvironmentOtherStore extends EnvironmentStore implements EnvironmentOtherStoreOperations{

	public EnvironmentOtherStore(SimEnvironment pSimEnvironment) {
		super(pSimEnvironment, "Other");
	}

}

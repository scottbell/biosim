package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.server.simulation.environment.EnvironmentNitrogenStoreOperations;

public class EnvironmentNitrogenStore extends EnvironmentStore implements EnvironmentNitrogenStoreOperations{

	public EnvironmentNitrogenStore(SimEnvironment pSimEnvironment) {
		super(pSimEnvironment, "Nitrogen");
	}
}

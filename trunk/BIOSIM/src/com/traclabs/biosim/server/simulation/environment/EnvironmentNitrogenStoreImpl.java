package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.idl.simulation.environment.EnvironmentNitrogenStoreOperations;

public class EnvironmentNitrogenStoreImpl extends EnvironmentStoreImpl implements EnvironmentNitrogenStoreOperations{

	public EnvironmentNitrogenStoreImpl(SimEnvironmentImpl pSimEnvironmentImpl) {
		super(pSimEnvironmentImpl, "Nitrogen");
	}
}

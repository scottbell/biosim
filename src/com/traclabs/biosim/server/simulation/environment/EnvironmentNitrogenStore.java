package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.server.simulation.air.INitrogenStore;

public class EnvironmentNitrogenStore extends EnvironmentStore implements INitrogenStore {

    public EnvironmentNitrogenStore(SimEnvironment pSimEnvironment) {
        super(pSimEnvironment, "Nitrogen");
    }
}

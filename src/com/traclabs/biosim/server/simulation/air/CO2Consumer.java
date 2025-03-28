package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.simulation.framework.ISimBioModule;

public interface CO2Consumer extends ISimBioModule {
    CO2ConsumerDefinition getCO2ConsumerDefinition();
}

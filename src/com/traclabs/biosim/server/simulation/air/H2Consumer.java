package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.simulation.framework.ISimBioModule;

public interface H2Consumer extends ISimBioModule {
    H2ConsumerDefinition getH2ConsumerDefinition();
}

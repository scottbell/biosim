package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.simulation.framework.ISimBioModule;

public interface H2Producer extends ISimBioModule {
    H2ProducerDefinition getH2ProducerDefinition();
}

package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.simulation.framework.ISimBioModule;

public interface O2Producer extends ISimBioModule {
    O2ProducerDefinition getO2ProducerDefinition();
}

package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.simulation.framework.ISimBioModule;

public interface NitrogenConsumer extends ISimBioModule {
    NitrogenConsumerDefinition getNitrogenConsumerDefinition();
}

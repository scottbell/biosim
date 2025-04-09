package com.traclabs.biosim.server.simulation.power;

import com.traclabs.biosim.server.simulation.framework.ISimBioModule;

public interface PowerConsumer extends ISimBioModule {
    PowerConsumerDefinition getPowerConsumerDefinition();
}

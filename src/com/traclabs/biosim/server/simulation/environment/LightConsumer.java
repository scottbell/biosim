package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.server.simulation.framework.ISimBioModule;

public interface LightConsumer extends ISimBioModule {
    LightConsumerDefinition getLightConsumerDefinition();
}

package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.server.simulation.framework.ISimBioModule;

public interface WaterConsumer extends ISimBioModule {
    WaterConsumerDefinition getWaterConsumerDefinition();
}

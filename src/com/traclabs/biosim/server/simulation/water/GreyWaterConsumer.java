package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.server.simulation.framework.ISimBioModule;

public interface GreyWaterConsumer extends ISimBioModule {
    GreyWaterConsumerDefinition getGreyWaterConsumerDefinition();
}

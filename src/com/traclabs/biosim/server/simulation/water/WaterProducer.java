package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.server.simulation.framework.ISimBioModule;

public interface WaterProducer extends ISimBioModule {
    WaterProducerDefinition getWaterProducerDefinition();
}

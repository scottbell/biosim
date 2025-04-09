package com.traclabs.biosim.server.simulation.power;

import com.traclabs.biosim.server.simulation.framework.ISimBioModule;

public interface PowerProducer extends ISimBioModule {
    PowerProducerDefinition getPowerProducerDefinition();
}

package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.simulation.framework.ISimBioModule;

public interface MethaneProducer extends ISimBioModule {
    MethaneProducerDefinition getMethaneProducerDefinition();
}

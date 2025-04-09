package com.traclabs.biosim.server.simulation.food;

import com.traclabs.biosim.server.simulation.framework.ISimBioModule;

public interface BiomassProducer extends ISimBioModule {
    BiomassProducerDefinition getBiomassProducerDefinition();
}

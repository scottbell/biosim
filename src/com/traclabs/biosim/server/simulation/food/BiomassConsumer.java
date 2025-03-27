package com.traclabs.biosim.server.simulation.food;
import com.traclabs.biosim.server.simulation.framework.ISimBioModule;

public interface BiomassConsumer extends ISimBioModule {
    BiomassConsumerDefinition getBiomassConsumerDefinition();
}

package com.traclabs.biosim.server.simulation.air;
import com.traclabs.biosim.server.simulation.framework.ISimBioModule;

public interface MethaneConsumer extends ISimBioModule {
    MethaneConsumerDefinition getMethaneConsumerDefinition();
}

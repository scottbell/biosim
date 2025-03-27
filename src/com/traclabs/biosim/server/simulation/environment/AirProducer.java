package com.traclabs.biosim.server.simulation.environment;
import com.traclabs.biosim.server.simulation.framework.ISimBioModule;

public interface AirProducer extends ISimBioModule {
    AirProducerDefinition getAirProducerDefinition();
}

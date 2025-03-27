package com.traclabs.biosim.server.simulation.environment;
import com.traclabs.biosim.server.simulation.framework.ISimBioModule;

public interface AirConsumer extends ISimBioModule {
    AirConsumerDefinition getAirConsumerDefinition();
}

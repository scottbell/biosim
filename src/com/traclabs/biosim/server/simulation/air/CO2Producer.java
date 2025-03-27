package com.traclabs.biosim.server.simulation.air;
import com.traclabs.biosim.server.simulation.framework.ISimBioModule;

public interface CO2Producer extends ISimBioModule {
    CO2ProducerDefinition getCO2ProducerDefinition();
}

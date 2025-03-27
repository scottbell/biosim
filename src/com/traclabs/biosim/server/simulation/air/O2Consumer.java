package com.traclabs.biosim.server.simulation.air;
import com.traclabs.biosim.server.simulation.framework.ISimBioModule;

public interface O2Consumer extends ISimBioModule {
    O2ConsumerDefinition getO2ConsumerDefinition();
}

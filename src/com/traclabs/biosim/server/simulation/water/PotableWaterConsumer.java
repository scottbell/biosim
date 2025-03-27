package com.traclabs.biosim.server.simulation.water;
import com.traclabs.biosim.server.simulation.framework.ISimBioModule;

public interface PotableWaterConsumer extends ISimBioModule {
    PotableWaterConsumerDefinition getPotableWaterConsumerDefinition();
}

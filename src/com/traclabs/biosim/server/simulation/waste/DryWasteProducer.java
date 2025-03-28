package com.traclabs.biosim.server.simulation.waste;

import com.traclabs.biosim.server.simulation.framework.ISimBioModule;

public interface DryWasteProducer extends ISimBioModule {
    DryWasteProducerDefinition getDryWasteProducerDefinition();
}

package com.traclabs.biosim.server.simulation.food;
import com.traclabs.biosim.server.simulation.framework.ISimBioModule;

public interface FoodProducer extends ISimBioModule {
    FoodProducerDefinition getFoodProducerDefinition();
}

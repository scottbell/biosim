package com.traclabs.biosim.server.simulation.food;
import com.traclabs.biosim.server.simulation.framework.ISimBioModule;

public interface FoodConsumer extends ISimBioModule {
    FoodConsumerDefinition getFoodConsumerDefinition();
}

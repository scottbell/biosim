package com.traclabs.biosim.server.actuator.food;

import com.traclabs.biosim.server.actuator.framework.GenericActuator;
import com.traclabs.biosim.server.simulation.food.FoodProcessor;

public abstract class FoodProcessorActuator extends GenericActuator {
    protected FoodProcessor myFoodProcessor;

    public FoodProcessorActuator(int pID, String pName) {
        super(pID, pName);
    }

    public void setOutput(FoodProcessor source) {
        myFoodProcessor = source;
    }

    public FoodProcessor getOutput() {
        return myFoodProcessor;
    }
}
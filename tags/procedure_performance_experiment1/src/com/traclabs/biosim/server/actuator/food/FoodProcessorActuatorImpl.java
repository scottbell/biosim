package com.traclabs.biosim.server.actuator.food;

import com.traclabs.biosim.idl.actuator.food.FoodProcessorActuatorOperations;
import com.traclabs.biosim.idl.simulation.food.FoodProcessor;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public abstract class FoodProcessorActuatorImpl extends GenericActuatorImpl
        implements FoodProcessorActuatorOperations {
    protected FoodProcessor myFoodProcessor;

    public FoodProcessorActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    public void setOutput(FoodProcessor source) {
        myFoodProcessor = source;
    }

    public FoodProcessor getOutput() {
        return myFoodProcessor;
    }
}
package com.traclabs.biosim.server.sensor.food;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.food.FoodProcessorSensorOperations;
import com.traclabs.biosim.idl.simulation.food.FoodProcessor;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public abstract class FoodProcessorSensorImpl extends GenericSensorImpl
        implements FoodProcessorSensorOperations {
    protected FoodProcessor myFoodProcessor;

    public FoodProcessorSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected abstract void gatherData();

    protected abstract void notifyListeners();

    public void setInput(FoodProcessor source) {
        myFoodProcessor = source;
    }

    public FoodProcessor getInput() {
        return myFoodProcessor;
    }

    public BioModule getInputModule() {
        return (BioModule) (getInput());
    }
}
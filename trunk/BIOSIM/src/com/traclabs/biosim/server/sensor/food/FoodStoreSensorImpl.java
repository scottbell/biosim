package com.traclabs.biosim.server.sensor.food;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.food.FoodStoreSensorOperations;
import com.traclabs.biosim.idl.simulation.food.FoodStore;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public abstract class FoodStoreSensorImpl extends GenericSensorImpl implements
        FoodStoreSensorOperations {
    protected FoodStore myFoodStore;

    public FoodStoreSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected abstract void gatherData();

    protected abstract void notifyListeners();

    public void setInput(FoodStore source) {
        myFoodStore = source;
    }

    public FoodStore getInput() {
        return myFoodStore;
    }

    public float getMax() {
        return myFoodStore.getLevel();
    }

    public BioModule getInputModule() {
        return (BioModule) (getInput());
    }
}
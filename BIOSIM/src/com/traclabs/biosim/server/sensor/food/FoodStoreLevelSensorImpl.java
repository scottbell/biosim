package com.traclabs.biosim.server.sensor.food;

import com.traclabs.biosim.idl.sensor.food.FoodStoreLevelSensorOperations;

public class FoodStoreLevelSensorImpl extends FoodStoreSensorImpl implements
        FoodStoreLevelSensorOperations {
    public FoodStoreLevelSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getLevel();
        myValue = randomFilter(preFilteredValue);
    }

    protected void notifyListeners() {
    }
}
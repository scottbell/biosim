package com.traclabs.biosim.server.sensor.framework;


public class StoreLevelSensor extends StoreSensor {
    public StoreLevelSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getCurrentLevel();
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }

    protected void notifyListeners() {
    }
}
package com.traclabs.biosim.server.sensor.framework;


public class StoreOverflowSensor extends StoreSensor {
    public StoreOverflowSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getOverflow();
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }

    protected void notifyListeners() {
    }
}
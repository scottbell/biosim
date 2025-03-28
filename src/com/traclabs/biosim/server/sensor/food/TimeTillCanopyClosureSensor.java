package com.traclabs.biosim.server.sensor.food;


public class TimeTillCanopyClosureSensor extends ShelfSensor {
    public TimeTillCanopyClosureSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredBooleanValue = getInput().getTimeTillCanopyClosure();
        myValue = getStochasticFilter().randomFilter(preFilteredBooleanValue);
    }

    public float getMax() {
        return 1f;
    }

    protected void notifyListeners() {
        //does nothing now
    }

}
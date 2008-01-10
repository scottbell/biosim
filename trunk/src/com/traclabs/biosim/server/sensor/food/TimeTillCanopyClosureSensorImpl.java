package com.traclabs.biosim.server.sensor.food;

import com.traclabs.biosim.idl.sensor.food.TimeTillCanopyClosureSensorOperations;


public class TimeTillCanopyClosureSensorImpl extends ShelfSensorImpl implements TimeTillCanopyClosureSensorOperations {
    public TimeTillCanopyClosureSensorImpl(int pID, String pName) {
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
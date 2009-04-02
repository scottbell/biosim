package com.traclabs.biosim.server.sensor.framework;

import com.traclabs.biosim.idl.sensor.framework.StoreOverflowSensorOperations;

public class StoreOverflowSensorImpl extends StoreSensorImpl implements
        StoreOverflowSensorOperations {
    public StoreOverflowSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getOverflow();
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }

    protected void notifyListeners() {
    }
}
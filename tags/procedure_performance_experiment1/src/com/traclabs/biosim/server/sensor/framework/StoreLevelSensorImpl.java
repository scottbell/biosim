package com.traclabs.biosim.server.sensor.framework;

import com.traclabs.biosim.idl.sensor.framework.StoreLevelSensorOperations;

public class StoreLevelSensorImpl extends StoreSensorImpl implements
        StoreLevelSensorOperations {
    public StoreLevelSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getCurrentLevel();
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }

    protected void notifyListeners() {
    }
}
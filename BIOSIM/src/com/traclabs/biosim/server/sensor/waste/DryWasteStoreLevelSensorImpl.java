package com.traclabs.biosim.server.sensor.waste;

import com.traclabs.biosim.idl.sensor.waste.DryWasteStoreLevelSensorOperations;

public class DryWasteStoreLevelSensorImpl extends DryWasteStoreSensorImpl
        implements DryWasteStoreLevelSensorOperations {
    public DryWasteStoreLevelSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getCurrentLevel();
        myValue = randomFilter(preFilteredValue);
    }

    protected void notifyListeners() {
    }
}
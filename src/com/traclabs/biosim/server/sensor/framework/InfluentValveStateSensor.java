package com.traclabs.biosim.server.sensor.framework;

import com.traclabs.biosim.server.framework.IBioModule;

public class InfluentValveStateSensor extends InfluentValveSensor  {
    public InfluentValveStateSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        int preFilteredValue = getInput().getIndexOfInfluentStore();
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }

    public float getMax() {
        return 1f;
    }

    protected void notifyListeners() {
    }

    public IBioModule getInputModule() {
        return getInput();
    }
}
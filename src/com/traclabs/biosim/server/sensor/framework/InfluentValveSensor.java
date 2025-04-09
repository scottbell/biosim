package com.traclabs.biosim.server.sensor.framework;

import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.simulation.framework.InfluentValve;

public abstract class InfluentValveSensor extends GenericSensor {
    protected InfluentValve myInfluentValve;

    public InfluentValveSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected abstract void gatherData();

    protected abstract void notifyListeners();

    public InfluentValve getInput() {
        return myInfluentValve;
    }

    public void setInput(InfluentValve source) {
        myInfluentValve = source;
    }

    public IBioModule getInputModule() {
        return getInput();
    }
}
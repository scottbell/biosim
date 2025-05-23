package com.traclabs.biosim.server.sensor.framework;

import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.simulation.framework.EffluentValve;

public abstract class EffluentValveSensor extends GenericSensor {
    protected EffluentValve myEffluentValve;

    public EffluentValveSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected abstract void gatherData();

    protected abstract void notifyListeners();

    public EffluentValve getInput() {
        return myEffluentValve;
    }

    public void setInput(EffluentValve source) {
        myEffluentValve = source;
    }

    public IBioModule getInputModule() {
        return getInput();
    }
}
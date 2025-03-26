package com.traclabs.biosim.server.sensor.framework;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.EffluentValve;

public abstract class EffluentValveSensor extends GenericSensor {
    protected EffluentValve myEffluentValve;

    public EffluentValveSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected abstract void gatherData();

    protected abstract void notifyListeners();

    public void setInput(EffluentValve source) {
        myEffluentValve = source;
    }

    public EffluentValve getInput() {
        return myEffluentValve;
    }

    public BioModule getInputModule() {
        return getInput();
    }
}
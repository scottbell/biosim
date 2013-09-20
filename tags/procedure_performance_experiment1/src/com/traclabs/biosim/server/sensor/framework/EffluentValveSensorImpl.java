package com.traclabs.biosim.server.sensor.framework;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.framework.EffluentValveSensorOperations;
import com.traclabs.biosim.idl.simulation.framework.EffluentValve;

public abstract class EffluentValveSensorImpl extends GenericSensorImpl implements
        EffluentValveSensorOperations {
    protected EffluentValve myEffluentValve;

    public EffluentValveSensorImpl(int pID, String pName) {
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
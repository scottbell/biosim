package com.traclabs.biosim.server.sensor.framework;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.framework.InfluentValveSensorOperations;
import com.traclabs.biosim.idl.simulation.framework.InfluentValve;

public abstract class InfluentValveSensorImpl extends GenericSensorImpl implements
        InfluentValveSensorOperations {
    protected InfluentValve myInfluentValve;

    public InfluentValveSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected abstract void gatherData();

    protected abstract void notifyListeners();

    public void setInput(InfluentValve source) {
        myInfluentValve = source;
    }

    public InfluentValve getInput() {
        return myInfluentValve;
    }

    public BioModule getInputModule() {
        return getInput();
    }
}
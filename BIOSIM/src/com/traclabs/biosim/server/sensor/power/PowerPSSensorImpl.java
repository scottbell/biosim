package com.traclabs.biosim.server.sensor.power;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.power.PowerPSSensorOperations;
import com.traclabs.biosim.idl.simulation.power.PowerPS;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public abstract class PowerPSSensorImpl extends GenericSensorImpl implements
        PowerPSSensorOperations {
    protected PowerPS myPowerPS;

    public PowerPSSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected abstract void gatherData();

    protected abstract void notifyListeners();

    public void setInput(PowerPS source) {
        myPowerPS = source;
    }

    public PowerPS getInput() {
        return myPowerPS;
    }

    public BioModule getInputModule() {
        return (BioModule) (getInput());
    }
}
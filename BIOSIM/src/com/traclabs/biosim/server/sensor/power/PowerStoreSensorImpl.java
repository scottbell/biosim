package com.traclabs.biosim.server.sensor.power;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.power.PowerStoreSensorOperations;
import com.traclabs.biosim.idl.simulation.power.PowerStore;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public abstract class PowerStoreSensorImpl extends GenericSensorImpl implements
        PowerStoreSensorOperations {
    protected PowerStore myPowerStore;

    public PowerStoreSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected abstract void gatherData();

    protected abstract void notifyListeners();

    public void setInput(PowerStore source) {
        myPowerStore = source;
    }

    public PowerStore getInput() {
        return myPowerStore;
    }

    public float getMax() {
        return myPowerStore.getCurrentCapacity();
    }

    public BioModule getInputModule() {
        return (BioModule) (getInput());
    }
}
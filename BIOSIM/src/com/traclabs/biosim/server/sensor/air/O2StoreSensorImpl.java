package com.traclabs.biosim.server.sensor.air;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.air.O2StoreSensorOperations;
import com.traclabs.biosim.idl.simulation.air.O2Store;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public abstract class O2StoreSensorImpl extends GenericSensorImpl implements
        O2StoreSensorOperations {
    private O2Store myO2Store;

    public O2StoreSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected abstract void gatherData();

    protected abstract void notifyListeners();

    public void setInput(O2Store source) {
        myO2Store = source;
    }

    public O2Store getInput() {
        return myO2Store;
    }

    public float getMax() {
        return myO2Store.getCurrentCapacity();
    }

    public BioModule getInputModule() {
        return (BioModule) (getInput());
    }
}
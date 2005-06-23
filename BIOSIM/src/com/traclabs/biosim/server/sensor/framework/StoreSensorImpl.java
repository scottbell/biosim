package com.traclabs.biosim.server.sensor.framework;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.framework.StoreSensorOperations;
import com.traclabs.biosim.idl.simulation.framework.Store;

public abstract class StoreSensorImpl extends GenericSensorImpl implements
        StoreSensorOperations {
    protected Store myStore;

    public StoreSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected abstract void gatherData();

    protected abstract void notifyListeners();

    public void setInput(Store source) {
        myStore = source;
    }

    public Store getInput() {
        return myStore;
    }

    public float getMax() {
        return myStore.getCurrentCapacity();
    }

    public BioModule getInputModule() {
        return getInput();
    }
}
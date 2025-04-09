package com.traclabs.biosim.server.sensor.framework;

import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.simulation.framework.Store;

public abstract class StoreSensor extends GenericSensor {
    protected Store myStore;

    public StoreSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected abstract void gatherData();

    protected abstract void notifyListeners();

    public Store getInput() {
        return myStore;
    }

    public void setInput(Store source) {
        myStore = source;
    }

    public float getMax() {
        return myStore.getCurrentCapacity();
    }

    public IBioModule getInputModule() {
        return getInput();
    }
}
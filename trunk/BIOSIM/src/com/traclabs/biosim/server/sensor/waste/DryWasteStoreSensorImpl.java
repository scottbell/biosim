package com.traclabs.biosim.server.sensor.waste;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.waste.DryWasteStoreSensorOperations;
import com.traclabs.biosim.idl.simulation.waste.DryWasteStore;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public abstract class DryWasteStoreSensorImpl extends GenericSensorImpl
        implements DryWasteStoreSensorOperations {
    protected DryWasteStore myDryWasteStore;

    public DryWasteStoreSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected abstract void gatherData();

    protected abstract void notifyListeners();

    public void setInput(DryWasteStore source) {
        myDryWasteStore = source;
    }

    public DryWasteStore getInput() {
        return myDryWasteStore;
    }

    public float getMax() {
        return myDryWasteStore.getCapacity();
    }

    public BioModule getInputModule() {
        return (BioModule) (getInput());
    }
}
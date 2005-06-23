package com.traclabs.biosim.server.sensor.air;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.air.NitrogenStoreSensorOperations;
import com.traclabs.biosim.idl.simulation.air.NitrogenStore;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public abstract class NitrogenStoreSensorImpl extends GenericSensorImpl
        implements NitrogenStoreSensorOperations {
    private NitrogenStore myNitrogenStore;

    public NitrogenStoreSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected abstract void gatherData();

    protected abstract void notifyListeners();

    public void setInput(NitrogenStore source) {
        myNitrogenStore = source;
    }

    public NitrogenStore getInput() {
        return myNitrogenStore;
    }

    public float getMax() {
        return myNitrogenStore.getCurrentCapacity();
    }

    public BioModule getInputModule() {
        return getInput();
    }
}
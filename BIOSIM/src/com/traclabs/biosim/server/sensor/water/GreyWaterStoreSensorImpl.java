package com.traclabs.biosim.server.sensor.water;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.water.GreyWaterStoreSensorOperations;
import com.traclabs.biosim.idl.simulation.water.GreyWaterStore;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public abstract class GreyWaterStoreSensorImpl extends GenericSensorImpl
        implements GreyWaterStoreSensorOperations {
    protected GreyWaterStore myGreyWaterStore;

    public GreyWaterStoreSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected abstract void gatherData();

    protected abstract void notifyListeners();

    public void setInput(GreyWaterStore source) {
        myGreyWaterStore = source;
    }

    public GreyWaterStore getInput() {
        return myGreyWaterStore;
    }

    public float getMax() {
        return myGreyWaterStore.getCurrentCapacity();
    }

    public BioModule getInputModule() {
        return getInput();
    }
}
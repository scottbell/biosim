package com.traclabs.biosim.server.sensor.water;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.water.WaterStoreSensorOperations;
import com.traclabs.biosim.idl.simulation.water.WaterStore;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public abstract class WaterStoreSensorImpl extends GenericSensorImpl implements
        WaterStoreSensorOperations {
    protected WaterStore myWaterStore;

    public WaterStoreSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected abstract void gatherData();

    protected abstract void notifyListeners();

    public void setInput(WaterStore source) {
        myWaterStore = source;
    }

    public WaterStore getInput() {
        return myWaterStore;
    }

    public float getMax() {
        return myWaterStore.getCurrentCapacity();
    }

    public BioModule getInputModule() {
        return (BioModule) (getInput());
    }
}
package com.traclabs.biosim.server.sensor.water;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.water.PotableWaterStoreSensorOperations;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStore;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public abstract class PotableWaterStoreSensorImpl extends GenericSensorImpl
        implements PotableWaterStoreSensorOperations {
    protected PotableWaterStore myPotableWaterStore;

    public PotableWaterStoreSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected abstract void gatherData();

    protected abstract void notifyListeners();

    public void setInput(PotableWaterStore source) {
        myPotableWaterStore = source;
    }

    public PotableWaterStore getInput() {
        return myPotableWaterStore;
    }

    public float getMax() {
        return myPotableWaterStore.getCurrentCapacity();
    }

    public BioModule getInputModule() {
        return getInput();
    }
}
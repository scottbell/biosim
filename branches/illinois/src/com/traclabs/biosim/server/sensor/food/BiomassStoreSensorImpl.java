package com.traclabs.biosim.server.sensor.food;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.food.BiomassStoreSensorOperations;
import com.traclabs.biosim.idl.simulation.food.BiomassStore;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public abstract class BiomassStoreSensorImpl extends GenericSensorImpl
        implements BiomassStoreSensorOperations {
    protected BiomassStore myBiomassStore;

    public BiomassStoreSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected abstract void gatherData();

    protected abstract void notifyListeners();

    public void setInput(BiomassStore source) {
        myBiomassStore = source;
    }

    public BiomassStore getInput() {
        return myBiomassStore;
    }

    public float getMax() {
        return myBiomassStore.getCurrentCapacity();
    }

    public BioModule getInputModule() {
        return getInput();
    }
}
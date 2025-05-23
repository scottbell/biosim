package com.traclabs.biosim.server.sensor.food;

import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;
import com.traclabs.biosim.server.simulation.food.BiomassStore;

public abstract class BiomassStoreSensor extends GenericSensor {
    protected BiomassStore myBiomassStore;

    public BiomassStoreSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected abstract void gatherData();

    protected abstract void notifyListeners();

    public BiomassStore getInput() {
        return myBiomassStore;
    }

    public void setInput(BiomassStore source) {
        myBiomassStore = source;
    }

    public float getMax() {
        return myBiomassStore.getCurrentCapacity();
    }

    public IBioModule getInputModule() {
        return getInput();
    }
}
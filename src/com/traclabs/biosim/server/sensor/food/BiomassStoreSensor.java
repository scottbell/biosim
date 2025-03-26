package com.traclabs.biosim.server.sensor.food;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;
import com.traclabs.biosim.server.simulation.food.BiomassStore;

public abstract class BiomassStoreSensor extends GenericSensor
         {
    protected BiomassStore myBiomassStore;

    public BiomassStoreSensor(int pID, String pName) {
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
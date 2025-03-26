package com.traclabs.biosim.server.sensor.framework;

import com.traclabs.biosim.server.framework.BioModule;

public class EffluentValveStateSensor extends EffluentValveSensor  {
    public EffluentValveStateSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getIndexOfEffluentStore();
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }

    public float getMax() {
        return 1f;
    }

    protected void notifyListeners() {
    }

    public BioModule getInputModule() {
        return getInput();
    }
}
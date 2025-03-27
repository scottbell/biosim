package com.traclabs.biosim.server.sensor.crew;

import com.traclabs.biosim.server.framework.BioModule;

public class CrewGroupDeathSensor extends CrewGroupSensor {
    public CrewGroupDeathSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        boolean preFilteredBooleanValue = getInput().isDead();
        if (preFilteredBooleanValue)
            myValue = getStochasticFilter().randomFilter(1);
        else
            myValue = getStochasticFilter().randomFilter(0);
    }

    public float getMax() {
        return 1f;
    }

    protected void notifyListeners() {
    }

    public IBioModule getInputModule() {
        return getInput();
    }
}
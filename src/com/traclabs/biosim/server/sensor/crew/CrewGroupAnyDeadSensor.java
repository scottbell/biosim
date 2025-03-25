package com.traclabs.biosim.server.sensor.crew;

import com.traclabs.biosim.server.framework.BioModule;

public class CrewGroupAnyDeadSensor extends CrewGroupSensor implements
        CrewGroupAnyDeadSensorOperations {
    public CrewGroupAnyDeadSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        boolean preFilteredBooleanValue = getInput().anyDead();
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

    public BioModule getInputModule() {
        return getInput();
    }
}
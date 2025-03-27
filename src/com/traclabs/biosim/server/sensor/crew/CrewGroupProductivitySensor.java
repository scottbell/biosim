package com.traclabs.biosim.server.sensor.crew;

import com.traclabs.biosim.server.framework.BioModule;

public class CrewGroupProductivitySensor extends CrewGroupSensor
         {
    public CrewGroupProductivitySensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getProductivity();
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }

    public float getMax() {
        return Float.MAX_VALUE;
    }

    protected void notifyListeners() {
    }

    public IBioModule getInputModule() {
        return getInput();
    }
}
package com.traclabs.biosim.server.sensor.framework;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.framework.InfluentValveStateSensorOperations;

public class InfluentValveStateSensorImpl extends InfluentValveSensorImpl implements
        InfluentValveStateSensorOperations {
    public InfluentValveStateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        int preFilteredValue = getInput().getIndexOfInfluentStore();
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
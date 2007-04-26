package com.traclabs.biosim.server.sensor.framework;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.framework.EffluentValveStateSensorOperations;

public class EffluentValveStateSensorImpl extends EffluentValveSensorImpl implements
        EffluentValveStateSensorOperations {
    public EffluentValveStateSensorImpl(int pID, String pName) {
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
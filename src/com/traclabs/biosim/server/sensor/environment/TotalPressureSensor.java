package com.traclabs.biosim.server.sensor.environment;

import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;
import com.traclabs.biosim.server.simulation.environment.SimEnvironment;

public class TotalPressureSensor extends GenericSensor {
    protected SimEnvironment myEnvironment;

    public TotalPressureSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        myValue = getStochasticFilter().randomFilter(myEnvironment.getTotalPressure());
    }

    @Override
    public float getMax() {
        return Float.MAX_VALUE;
    }

    public SimEnvironment getEnvironment() {
        return myEnvironment;
    }

    public SimEnvironment getInput() {
        return myEnvironment;
    }

    public void setInput(SimEnvironment environment) {
        myEnvironment = environment;
    }

    @Override
    public IBioModule getInputModule() {
        return myEnvironment;
    }
}
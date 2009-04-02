package com.traclabs.biosim.server.sensor.environment;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.environment.TotalPressureSensorOperations;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class TotalPressureSensorImpl extends GenericSensorImpl implements TotalPressureSensorOperations {
    protected SimEnvironment myEnvironment;

    public TotalPressureSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData(){
        myValue = getStochasticFilter().randomFilter(myEnvironment.getTotalPressure());
    }

	@Override
	public float getMax() {
		return Float.MAX_VALUE;
	}
	
	public void setInput(SimEnvironment environment) {
		myEnvironment = environment;
	}

	public SimEnvironment getEnvironment() {
		return myEnvironment;
	}

	public SimEnvironment getInput() {
		return myEnvironment;
	}

	@Override
	public BioModule getInputModule() {
		return myEnvironment;
	}
}
package com.traclabs.biosim.server.sensor.environment;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.environment.GasPressureSensorOperations;
import com.traclabs.biosim.idl.simulation.environment.EnvironmentStore;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class GasPressureSensorImpl extends GenericSensorImpl implements GasPressureSensorOperations {
    private EnvironmentStore myEnvironmentStore;
    
    public GasPressureSensorImpl() {
        super(0, "Unnamed GasPressureSensor");
    }

    public GasPressureSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData(){
        float preFilteredValue = getInput().getPressure();
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }

    public BioModule getInputModule() {
        return getInput();
    }

	@Override
	public float getMax() {
		return myEnvironmentStore.getCurrentCapacity();
	}

	public void setInput(EnvironmentStore source) {
		myEnvironmentStore = source;
	}

	public EnvironmentStore getInput() {
		return myEnvironmentStore;
	}
}
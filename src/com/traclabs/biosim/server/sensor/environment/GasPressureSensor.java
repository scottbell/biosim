package com.traclabs.biosim.server.sensor.environment;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.environment.EnvironmentStore;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;

public class GasPressureSensor extends GenericSensor  {
    private EnvironmentStore myEnvironmentStore;
    
    public GasPressureSensor() {
        super(0, "Unnamed GasPressureSensor");
    }

    public GasPressureSensor(int pID, String pName) {
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
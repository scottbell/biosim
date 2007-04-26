package com.traclabs.biosim.server.sensor.environment;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.environment.GasConcentrationSensorOperations;
import com.traclabs.biosim.idl.simulation.environment.EnvironmentStore;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class GasConcentrationSensorImpl extends GenericSensorImpl implements GasConcentrationSensorOperations {
    protected SimEnvironment myEnvironment;
    private EnvironmentStore myEnvironmentStore;

    public GasConcentrationSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData(){
        float molesOfGas = getGas().getCurrentLevel();
        float totalMoles = myEnvironment.getTotalMoles();
        if (totalMoles <= 0)
        	myValue = getStochasticFilter().randomFilter(0);
        else{
        	float preFilteredValue = molesOfGas / totalMoles;
        	myValue = getStochasticFilter().randomFilter(preFilteredValue);
        }
    }
    
	@Override
	public float getMax() {
		return 1;
	}

	public void setInput(SimEnvironment environment, EnvironmentStore gas) {
		myEnvironment = environment;
		myEnvironmentStore = gas;
	}

	public SimEnvironment getEnvironment() {
		return myEnvironment;
	}

	public EnvironmentStore getGas() {
		return myEnvironmentStore;
	}

	@Override
	public BioModule getInputModule() {
		return myEnvironmentStore;
	}
}
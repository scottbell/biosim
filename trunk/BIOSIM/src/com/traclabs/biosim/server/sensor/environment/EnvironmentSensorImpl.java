package com.traclabs.biosim.server.sensor.environment;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.environment.EnvironmentSensorOperations;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public abstract class EnvironmentSensorImpl extends GenericSensorImpl implements EnvironmentSensorOperations{
	protected SimEnvironment myEnvironment;
	
	public EnvironmentSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected abstract void gatherData();
	protected abstract void notifyListeners();

	public void setInput(SimEnvironment source){
		myEnvironment = source;
	}
	
	public SimEnvironment getInput(){
		return myEnvironment;
	}
	
	public BioModule getInputModule(){
		return (BioModule)(getInput());
	}
}

package com.traclabs.biosim.server.actuator.environment;

import com.traclabs.biosim.idl.actuator.environment.EnvironmentActuatorOperations;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public abstract class EnvironmentActuatorImpl extends GenericActuatorImpl implements EnvironmentActuatorOperations{
	protected SimEnvironment myEnvironment;
	
	public EnvironmentActuatorImpl(int pID, String pName){
		super(pID, pName);
	}
	
	public void setOutput(SimEnvironment source){
		myEnvironment = source;
	}
	
	public SimEnvironment getOutput(){
		return myEnvironment;
	}
}

package com.traclabs.biosim.server.actuator.environment;

import com.traclabs.biosim.idl.actuator.environment.CO2AirEnvironmentOutFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.framework.CO2AirProducer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class CO2AirEnvironmentOutFlowRateActuatorImpl extends GenericActuatorImpl implements CO2AirEnvironmentOutFlowRateActuatorOperations{
	private CO2AirProducer myProducer;
	private int myIndex;
	
	public CO2AirEnvironmentOutFlowRateActuatorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setCO2AirEnvironmentOutputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(CO2AirProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public BioModule getOutputModule(){
		return (BioModule)(myProducer);
	}
	
	public float getMax(){
		return myProducer.getCO2AirEnvironmentOutputMaxFlowRate(myIndex);
	}
	
	public CO2AirProducer getOutput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
}

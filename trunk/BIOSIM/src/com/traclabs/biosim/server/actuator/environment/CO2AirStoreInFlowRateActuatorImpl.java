package com.traclabs.biosim.server.actuator.environment;

import com.traclabs.biosim.idl.actuator.environment.CO2AirStoreInFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.framework.CO2AirConsumer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class CO2AirStoreInFlowRateActuatorImpl extends GenericActuatorImpl implements CO2AirStoreInFlowRateActuatorOperations{
	private CO2AirConsumer myConsumer;
	private int myIndex;
	
	public CO2AirStoreInFlowRateActuatorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setCO2AirStoreInputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(CO2AirConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public float getMax(){
		return myConsumer.getCO2AirStoreInputMaxFlowRate(myIndex);
	}
	
	public BioModule getOutputModule(){
		return (BioModule)(myConsumer);
	}
	
	public CO2AirConsumer getOutput(){
		return myConsumer;
	}
	
	public int getIndex(){
		return myIndex;
	}
}

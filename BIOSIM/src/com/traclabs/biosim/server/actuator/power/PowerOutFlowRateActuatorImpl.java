package com.traclabs.biosim.server.actuator.power;

import com.traclabs.biosim.idl.actuator.power.PowerOutFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.framework.PowerProducer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class PowerOutFlowRateActuatorImpl extends GenericActuatorImpl implements PowerOutFlowRateActuatorOperations{
	private PowerProducer myProducer;
	private int myIndex;
	
	public PowerOutFlowRateActuatorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setPowerOutputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(PowerProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public BioModule getOutputModule(){
		return (BioModule)(myProducer);
	}
	
	public PowerProducer getOutput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	public float getMax(){
		return myProducer.getPowerOutputMaxFlowRate(myIndex);
	}
}

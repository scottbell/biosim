package com.traclabs.biosim.server.actuator.air;

import com.traclabs.biosim.idl.actuator.air.H2OutFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.framework.H2Producer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class H2OutFlowRateActuatorImpl extends GenericActuatorImpl implements H2OutFlowRateActuatorOperations{
	private H2Producer myProducer;
	private int myIndex;
	
	public H2OutFlowRateActuatorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setH2OutputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(H2Producer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public BioModule getOutputModule(){
		return (BioModule)(myProducer);
	}
	
	public H2Producer getOutput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	public float getMax(){
		return myProducer.getH2OutputMaxFlowRate(myIndex);
	}
}

package com.traclabs.biosim.server.actuator.water;

import com.traclabs.biosim.idl.actuator.water.DirtyWaterInFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.framework.DirtyWaterConsumer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class DirtyWaterInFlowRateActuatorImpl extends GenericActuatorImpl implements DirtyWaterInFlowRateActuatorOperations{
	private DirtyWaterConsumer myConsumer;
	private int myIndex;
	
	public DirtyWaterInFlowRateActuatorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setDirtyWaterInputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(DirtyWaterConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public BioModule getOutputModule(){
		return (BioModule)(myConsumer);
	}
	
	public DirtyWaterConsumer getOutput(){
		return myConsumer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	public float getMax(){
		return myConsumer.getDirtyWaterInputMaxFlowRate(myIndex);
	}
}

package com.traclabs.biosim.server.sensor.air;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.framework.H2Producer;
import com.traclabs.biosim.idl.sensor.air.H2OutFlowRateSensorOperations;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class H2OutFlowRateSensorImpl extends GenericSensorImpl implements H2OutFlowRateSensorOperations{
	private H2Producer myProducer;
	private int myIndex;
	
	public H2OutFlowRateSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getH2OutputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(H2Producer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public float getMax(){
		return myProducer.getH2OutputMaxFlowRate(myIndex);
	}
	
	public H2Producer getInput(){
		return myProducer;
	}
	
	public BioModule getInputModule(){
		return (BioModule)(myProducer);
	}
	
	public int getIndex(){
		return myIndex;
	}
	
}

package com.traclabs.biosim.server.sensor.water;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.framework.WaterConsumer;
import com.traclabs.biosim.idl.sensor.water.WaterInFlowRateSensorOperations;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class WaterInFlowRateSensorImpl extends GenericSensorImpl implements WaterInFlowRateSensorOperations{
	private WaterConsumer myConsumer;
	private int myIndex;
	
	public WaterInFlowRateSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getWaterInputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(WaterConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public WaterConsumer getInput(){
		return myConsumer;
	}
	
	public float getMax(){
		return myConsumer.getWaterInputMaxFlowRate(myIndex);
	}
	
	public BioModule getInputModule(){
		return (BioModule)(myConsumer);
	}
	
	public int getIndex(){
		return myIndex;
	}
}

package com.traclabs.biosim.server.sensor.water;

import com.traclabs.biosim.idl.sensor.water.GreyWaterStoreLevelSensorOperations;

public class GreyWaterStoreLevelSensorImpl extends GreyWaterStoreSensorImpl implements GreyWaterStoreLevelSensorOperations{
	public GreyWaterStoreLevelSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getLevel();
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
	}
}

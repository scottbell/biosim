package com.traclabs.biosim.server.sensor.water;

import com.traclabs.biosim.idl.sensor.water.WaterStoreLevelSensorOperations;

public class WaterStoreLevelSensorImpl extends WaterStoreSensorImpl implements WaterStoreLevelSensorOperations{
	public WaterStoreLevelSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getLevel();
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
	}
}

package com.traclabs.biosim.server.sensor.water;

import com.traclabs.biosim.idl.sensor.water.PotableWaterStoreLevelSensorOperations;

public class PotableWaterStoreLevelSensorImpl extends PotableWaterStoreSensorImpl implements PotableWaterStoreLevelSensorOperations{
	public PotableWaterStoreLevelSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getLevel();
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
	}
}

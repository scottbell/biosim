package com.traclabs.biosim.server.sensor.air;

import com.traclabs.biosim.idl.sensor.air.CO2StoreLevelSensorOperations;

public class CO2StoreLevelSensorImpl extends CO2StoreSensorImpl implements CO2StoreLevelSensorOperations{
	public CO2StoreLevelSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getLevel();
		myValue = randomFilter(preFilteredValue);
	}
	protected void notifyListeners(){
		//Does nothing right now..
	}
	
}

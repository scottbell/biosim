package com.traclabs.biosim.server.sensor.air;

import com.traclabs.biosim.idl.sensor.air.H2StoreLevelSensorOperations;

public class H2StoreLevelSensorImpl extends H2StoreSensorImpl implements H2StoreLevelSensorOperations{
	public H2StoreLevelSensorImpl(int pID, String pName){
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

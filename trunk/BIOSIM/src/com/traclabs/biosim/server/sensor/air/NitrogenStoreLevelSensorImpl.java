package com.traclabs.biosim.server.sensor.air;

import com.traclabs.biosim.idl.sensor.air.NitrogenStoreLevelSensorOperations;

public class NitrogenStoreLevelSensorImpl extends NitrogenStoreSensorImpl implements NitrogenStoreLevelSensorOperations{
	public NitrogenStoreLevelSensorImpl(int pID, String pName){
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

package com.traclabs.biosim.server.sensor.food;

import com.traclabs.biosim.idl.sensor.food.BiomassStoreLevelSensorOperations;

public class BiomassStoreLevelSensorImpl extends BiomassStoreSensorImpl implements BiomassStoreLevelSensorOperations{
	public BiomassStoreLevelSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getLevel();
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
	}
}

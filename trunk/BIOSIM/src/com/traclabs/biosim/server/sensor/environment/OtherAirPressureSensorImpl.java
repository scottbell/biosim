package com.traclabs.biosim.server.sensor.environment;

import com.traclabs.biosim.idl.sensor.environment.OtherAirPressureSensorOperations;

public class OtherAirPressureSensorImpl extends EnvironmentSensorImpl implements OtherAirPressureSensorOperations{
	public OtherAirPressureSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getOtherPressure();
		myValue = randomFilter(preFilteredValue);
	}

	protected void notifyListeners(){
	}
	
	public float getMax(){
		return 1f;
	}

}

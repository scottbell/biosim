package com.traclabs.biosim.server.sensor.environment;

import com.traclabs.biosim.idl.sensor.environment.CO2AirPressureSensorOperations;

public class CO2AirPressureSensorImpl extends EnvironmentSensorImpl implements CO2AirPressureSensorOperations{
	public CO2AirPressureSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getCO2Pressure();
		myValue = randomFilter(preFilteredValue);
	}

	protected void notifyListeners(){
	}
	
	public float getMax(){
		return 1f;
	}
}

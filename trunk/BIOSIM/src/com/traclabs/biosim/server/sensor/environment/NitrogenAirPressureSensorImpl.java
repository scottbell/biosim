package com.traclabs.biosim.server.sensor.environment;

import com.traclabs.biosim.idl.sensor.environment.NitrogenAirPressureSensorOperations;

public class NitrogenAirPressureSensorImpl extends EnvironmentSensorImpl implements NitrogenAirPressureSensorOperations{
	public NitrogenAirPressureSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getNitrogenPressure();
		myValue = randomFilter(preFilteredValue);
	}

	protected void notifyListeners(){
	}
	
	public float getMax(){
		return 1f;
	}
	
}

package com.traclabs.biosim.server.sensor.environment;

import com.traclabs.biosim.idl.sensor.environment.NitrogenAirConcentrationSensorOperations;

public class NitrogenAirConcentrationSensorImpl extends EnvironmentSensorImpl implements NitrogenAirConcentrationSensorOperations{
	public NitrogenAirConcentrationSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getNitrogenMoles() / getInput().getTotalMoles();
		myValue = randomFilter(preFilteredValue);
	}

	protected void notifyListeners(){
	}
	
	public float getMax(){
		return 1f;
	}
}

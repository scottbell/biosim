package biosim.server.sensor.environment;

import biosim.idl.sensor.environment.O2AirPressureSensorOperations;

public class O2AirPressureSensorImpl extends EnvironmentSensorImpl implements O2AirPressureSensorOperations{
	public O2AirPressureSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getO2Pressure();
		myValue = randomFilter(preFilteredValue);
	}

	protected void notifyListeners(){
	}
	
	public float getMax(){
		return 1f;
	}
	
}

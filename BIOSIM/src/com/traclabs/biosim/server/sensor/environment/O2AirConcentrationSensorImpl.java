package biosim.server.sensor.environment;

import biosim.idl.sensor.environment.O2AirConcentrationSensorOperations;

public class O2AirConcentrationSensorImpl extends EnvironmentSensorImpl implements O2AirConcentrationSensorOperations{
	public O2AirConcentrationSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getO2Moles() / getInput().getTotalMoles();
		myValue = randomFilter(preFilteredValue);
	}

	protected void notifyListeners(){
	}
	
	public float getMax(){
		return 1f;
	}
}

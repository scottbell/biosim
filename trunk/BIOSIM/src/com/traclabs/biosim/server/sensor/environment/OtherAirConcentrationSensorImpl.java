package biosim.server.sensor.environment;

import biosim.idl.sensor.environment.OtherAirConcentrationSensorOperations;

public class OtherAirConcentrationSensorImpl extends EnvironmentSensorImpl implements OtherAirConcentrationSensorOperations{
	public OtherAirConcentrationSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getOtherMoles() / getInput().getTotalMoles();
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
	}
	
	public float getMax(){
		return 1f;
	}
}

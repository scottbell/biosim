package biosim.server.sensor.environment;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.simulation.environment.*;

public class CO2AirConcentrationSensorImpl extends EnvironmentSensorImpl implements CO2AirConcentrationSensorOperations{
	public CO2AirConcentrationSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getCO2Moles() / getInput().getTotalMoles();
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
	}
	
	public float getMax(){
		return 1f;
	}
	
}

package biosim.server.sensor.environment;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.simulation.environment.*;

public class OtherAirConcentrationSensorImpl extends EnvironmentSensorImpl implements OtherAirConcentrationSensorOperations{
	public OtherAirConcentrationSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getOtherMoles() / getInput().getTotalMoles();
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
	}
	
	/**
	* Returns the name of this module (OtherAirConcentrationSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "OtherAirConcentrationSensor"+getID();
	}
}

package biosim.server.sensor.environment;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.simulation.environment.*;

public class CO2AirConcentrationSensorImpl extends EnvironmentSensorImpl implements CO2AirConcentrationSensorOperations{
	public CO2AirConcentrationSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getCO2Moles();
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
	}
	
	/**
	* Returns the name of this module (CO2AirConcentrationSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "CO2AirConcentrationSensor"+getID();
	}
}

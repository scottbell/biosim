package biosim.server.sensor.environment;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.simulation.environment.*;

public class CO2AirMolesSensorImpl extends EnvironmentSensorImpl implements CO2AirMolesSensorOperations{
	public CO2AirMolesSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getCO2Moles();
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
	}
	
	/**
	* Returns the name of this module (CO2AirMolesSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "CO2AirMolesSensor"+getID();
	}
}

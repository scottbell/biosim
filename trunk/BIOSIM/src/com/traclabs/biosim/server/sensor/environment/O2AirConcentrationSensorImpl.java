package biosim.server.sensor.environment;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.simulation.environment.*;

public class O2AirMolesSensorImpl extends EnvironmentSensorImpl implements O2AirMolesSensorOperations{
	public O2AirMolesSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getO2Moles();
		myValue = randomFilter(preFilteredValue);
	}

	protected void notifyListeners(){
	}
	
	/**
	* Returns the name of this module (O2AirMolesSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "O2AirMolesSensor"+getID();
	}
}

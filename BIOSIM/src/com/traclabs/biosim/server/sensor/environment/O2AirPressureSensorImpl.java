package biosim.server.sensor.environment;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.simulation.environment.*;

public class O2AirPressureSensorImpl extends EnvironmentSensorImpl implements O2AirPressureSensorOperations{
	public O2AirPressureSensorImpl(int pID){
		super(pID);
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
	
	/**
	* Returns the name of this module (O2AirPressureSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "O2AirPressureSensor"+getID();
	}
}

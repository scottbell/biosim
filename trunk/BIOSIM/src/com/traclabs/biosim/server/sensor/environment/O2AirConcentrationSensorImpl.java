package biosim.server.sensor.environment;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.simulation.environment.*;

public class O2AirConcentrationSensorImpl extends EnvironmentSensorImpl implements O2AirConcentrationSensorOperations{
	public O2AirConcentrationSensorImpl(int pID){
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
	* Returns the name of this module (O2AirConcentrationSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "O2AirConcentrationSensor"+getID();
	}
}

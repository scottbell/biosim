package biosim.server.sensor.power;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.power.*;
import biosim.idl.simulation.power.*;

public class PowerStoreLevelSensorImpl extends PowerStoreSensorImpl implements PowerStoreLevelSensorOperations{
	public PowerStoreLevelSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getLevel();
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
	}
	
	/**
	* Returns the name of this module (PowerStoreLevelSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "PowerStoreLevelSensor"+getID();
	}
}

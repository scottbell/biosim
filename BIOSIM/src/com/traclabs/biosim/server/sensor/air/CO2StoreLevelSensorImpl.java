package biosim.server.sensor.air;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.air.*;
import biosim.idl.framework.*;

public class CO2StoreLevelSensorImpl extends CO2StoreSensorImpl implements CO2StoreLevelSensorOperations{
	public CO2StoreLevelSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getLevel();
		myValue = randomFilter(preFilteredValue);
	}
	protected void notifyListeners(){
		//Does nothing right now..
	}
	
	/**
	* Returns the name of this module (CO2StoreLevelSensorImpl)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "CO2StoreLevelSensor"+getID();
	}
}

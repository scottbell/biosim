package biosim.server.sensor.air;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.air.*;
import biosim.idl.simulation.air.*;

public abstract class O2StoreLevelSensorImpl extends O2StoreSensorImpl implements O2StoreLevelSensorOperations{
	public O2StoreLevelSensorImpl(int pID){
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
	* Returns the name of this module (O2StoreLevelSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "O2StoreLevelSensor"+getID();
	}
}

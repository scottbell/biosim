package biosim.server.sensor.food;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.food.*;
import biosim.idl.framework.*;

public class BiomassStoreLevelSensorImpl extends BiomassStoreSensorImpl implements BiomassStoreLevelSensorOperations{
	public BiomassStoreLevelSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getLevel();
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
	}
	
	/**
	* Returns the name of this module (BiomassStoreLevelSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "BiomassStoreLevelSensor"+getID();
	}
}

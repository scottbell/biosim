package biosim.server.sensor.crew;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.crew.*;
import biosim.idl.framework.*;

public class CrewGroupDeathSensorImpl extends CrewGroupSensorImpl implements CrewGroupDeathSensorOperations{
	public CrewGroupDeathSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		boolean preFilteredBooleanValue = getInput().isDead();
		if (preFilteredBooleanValue)
			myValue = randomFilter(1);
		else
			myValue = randomFilter(0);
	}
	
	public float getMax(){
		return 1f;
	}
	
	protected void notifyListeners(){
	}
	
	/**
	* Returns the name of this module (CrewGroupDeathSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "CrewGroupDeathSensor"+getID();
	}
	
	protected BioModule getInputModule(){
		return (BioModule)(getInput());
	}
}

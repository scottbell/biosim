package biosim.server.sensor.crew;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.crew.*;
import biosim.idl.framework.*;
import biosim.idl.simulation.crew.*;

public abstract class CrewGroupSensorImpl extends GenericSensorImpl implements CrewGroupSensorOperations{
	protected CrewGroup myCrewGroup;
	
	public CrewGroupSensorImpl(int pID){
		super(pID);
	}

	protected abstract void gatherData();
	protected abstract void notifyListeners();

	public void setInput(CrewGroup source){
		myCrewGroup = source;
	}
	
	public CrewGroup getInput(){
		return myCrewGroup;
	}
	
	/**
	* Returns the name of this module (CrewGroupSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "CrewGroupSensor"+getID();
	}
	
	protected BioModule getInputModule(){
		return (BioModule)(getInput());
	}
}

package biosim.server.sensor.crew;

import biosim.idl.framework.BioModule;
import biosim.idl.sensor.crew.CrewGroupSensorOperations;
import biosim.idl.simulation.crew.CrewGroup;
import biosim.server.sensor.framework.GenericSensorImpl;

public abstract class CrewGroupSensorImpl extends GenericSensorImpl implements CrewGroupSensorOperations{
	protected CrewGroup myCrewGroup;
	
	public CrewGroupSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected abstract void gatherData();
	protected abstract void notifyListeners();

	public void setInput(CrewGroup source){
		myCrewGroup = source;
	}
	
	public CrewGroup getInput(){
		return myCrewGroup;
	}
	
	public BioModule getInputModule(){
		return (BioModule)(getInput());
	}
}

package biosim.server.sensor.crew;

import biosim.idl.framework.BioModule;
import biosim.idl.sensor.crew.CrewGroupProductivitySensorOperations;

public class CrewGroupProductivitySensorImpl extends CrewGroupSensorImpl implements CrewGroupProductivitySensorOperations{
	public CrewGroupProductivitySensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getProductivity();
		myValue = randomFilter(preFilteredValue);
	}
	
	public float getMax(){
		return Float.MAX_VALUE;
	}
	
	protected void notifyListeners(){
	}
	
	public BioModule getInputModule(){
		return (BioModule)(getInput());
	}
}

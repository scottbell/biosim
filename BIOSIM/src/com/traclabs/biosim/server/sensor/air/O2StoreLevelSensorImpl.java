package biosim.server.sensor.air;

import biosim.idl.sensor.air.O2StoreLevelSensorOperations;

public class O2StoreLevelSensorImpl extends O2StoreSensorImpl implements O2StoreLevelSensorOperations{
	public O2StoreLevelSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getLevel();
		myValue = randomFilter(preFilteredValue);
	}
	protected void notifyListeners(){
		//Does nothing right now..
	}
}

package biosim.server.sensor.framework;

import biosim.idl.sensor.framework.StoreLevelSensorOperations;

public class StoreLevelSensorImpl extends StoreSensorImpl implements StoreLevelSensorOperations{
	public StoreLevelSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getLevel();
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
	}
}

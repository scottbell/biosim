package biosim.server.sensor.power;

import biosim.idl.sensor.power.PowerStoreLevelSensorOperations;

public class PowerStoreLevelSensorImpl extends PowerStoreSensorImpl implements PowerStoreLevelSensorOperations{
	public PowerStoreLevelSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getLevel();
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
	}
}

package biosim.server.sensor.water;

import biosim.idl.sensor.water.DirtyWaterStoreLevelSensorOperations;

public class DirtyWaterStoreLevelSensorImpl extends DirtyWaterStoreSensorImpl implements DirtyWaterStoreLevelSensorOperations{
	public DirtyWaterStoreLevelSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getLevel();
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
	}
}

package biosim.server.sensor.waste;

import biosim.idl.sensor.waste.DryWasteStoreLevelSensorOperations;

public class DryWasteStoreLevelSensorImpl extends DryWasteStoreSensorImpl implements DryWasteStoreLevelSensorOperations{
	public DryWasteStoreLevelSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getLevel();
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
	}
}

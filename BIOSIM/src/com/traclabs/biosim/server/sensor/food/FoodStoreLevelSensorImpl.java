package biosim.server.sensor.food;

import biosim.idl.sensor.food.*;

public class FoodStoreLevelSensorImpl extends FoodStoreSensorImpl implements FoodStoreLevelSensorOperations{
	public FoodStoreLevelSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getLevel();
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
	}
}

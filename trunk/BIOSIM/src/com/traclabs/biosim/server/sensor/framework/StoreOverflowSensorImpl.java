package biosim.server.sensor.framework;

import biosim.idl.sensor.framework.StoreOverflowSensorOperations;

public class StoreOverflowSensorImpl extends StoreSensorImpl implements StoreOverflowSensorOperations{
	public StoreOverflowSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getOverflow();
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
	}
}

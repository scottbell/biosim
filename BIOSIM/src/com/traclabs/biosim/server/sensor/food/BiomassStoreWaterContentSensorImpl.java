package biosim.server.sensor.food;

import biosim.idl.sensor.food.BiomassStoreWaterContentSensorOperations;

public class BiomassStoreWaterContentSensorImpl extends BiomassStoreSensorImpl implements BiomassStoreWaterContentSensorOperations{
	public BiomassStoreWaterContentSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().calculateWaterContentInStore();
		myValue = randomFilter(preFilteredValue);
	}
	
	public float getMax(){
		return getInput().getCapacity();
	}
	
	protected void notifyListeners(){
		//does nothing now
	}

}

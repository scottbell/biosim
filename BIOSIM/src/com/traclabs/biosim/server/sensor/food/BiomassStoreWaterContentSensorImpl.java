package biosim.server.sensor.food;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.food.*;
import biosim.idl.framework.*;
import biosim.idl.simulation.food.*;

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

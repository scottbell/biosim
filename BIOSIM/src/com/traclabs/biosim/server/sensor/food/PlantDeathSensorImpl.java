package biosim.server.sensor.food;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.food.*;
import biosim.idl.framework.*;
import biosim.idl.simulation.food.*;

public class PlantDeathSensorImpl extends ShelfSensorImpl implements PlantDeathSensorOperations{
	public PlantDeathSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		boolean preFilteredBooleanValue = getInput().isDead();
		if (preFilteredBooleanValue)
			myValue = randomFilter(1);
		else
			myValue = randomFilter(0);
	}
	
	public float getMax(){
		return 1f;
	}
	
	protected void notifyListeners(){
		//does nothing now
	}

}

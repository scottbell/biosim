package biosim.server.sensor.air;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.air.*;
import biosim.idl.framework.*;

public class H2StoreLevelSensorImpl extends H2StoreSensorImpl implements H2StoreLevelSensorOperations{
	public H2StoreLevelSensorImpl(int pID, String pName){
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
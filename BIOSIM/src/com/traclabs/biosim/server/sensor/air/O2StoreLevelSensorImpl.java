package biosim.server.sensor.framework;

import biosim.server.framework.*;
import biosim.idl.sensor.framework.*;
import biosim.idl.sensor.air.*;
import biosim.idl.simulation.air.*;

public abstract class O2StoreLevelSensorImpl extends O2StoreSensorImpl implements O2StoreLevelSensorOperations{
	public O2StoreLevelSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		float preFilteredValue = myO2Store.getLevel();
		myValue = randomFilter(preFilteredValue);
	}
	protected void notifyListeners(){
		//Does nothing right now..
	}
}

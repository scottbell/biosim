package biosim.server.sensor.framework;

import biosim.server.framework.*;
import biosim.idl.sensor.framework.*;
import biosim.idl.sensor.air.*;
import biosim.idl.simulation.air.*;

public abstract class O2StoreSensorImpl extends GenericSensorImpl implements O2StoreSensorOperations{
	protected O2Store myO2Store;
	
	public O2StoreSensorImpl(int pID){
		super(pID);
	}

	protected abstract void gatherData();
	protected abstract void notifyListeners();

	public void setInput(O2Store source){
		myO2Store = source;
	}
	
	public O2Store getInput(){
		return myO2Store;
	}
}

package biosim.server.sensor.air;

import biosim.server.framework.*;
import biosim.idl.sensor.framework.*;
import biosim.idl.sensor.air.*;
import biosim.idl.simulation.air.*;

public abstract class CO2StoreSensorImpl extends GenericSensorImpl implements CO2StoreSensorOperations{
	protected CO2Store myCO2Store;
	
	public CO2StoreSensorImpl(int pID){
		super(pID);
	}

	protected abstract void gatherData();
	protected abstract void notifyListeners();

	public void setInput(CO2Store source){
		myCO2Store = source;
	}
	
	public CO2Store getInput(){
		return myCO2Store;
	}
}

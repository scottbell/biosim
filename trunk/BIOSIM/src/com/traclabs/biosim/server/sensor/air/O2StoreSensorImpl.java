package biosim.server.sensor.air;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.air.*;
import biosim.idl.framework.*;
import biosim.idl.simulation.air.*;

public abstract class O2StoreSensorImpl extends GenericSensorImpl implements O2StoreSensorOperations{
	private O2Store myO2Store;
	
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
	
	/**
	* Returns the name of this module (O2StoreSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "O2StoreSensor"+getID();
	}
}

package biosim.server.sensor.water;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.water.*;
import biosim.idl.framework.*;
import biosim.idl.simulation.water.*;

public abstract class DirtyWaterStoreSensorImpl extends GenericSensorImpl implements DirtyWaterStoreSensorOperations{
	protected DirtyWaterStore myDirtyWaterStore;
	
	public DirtyWaterStoreSensorImpl(int pID){
		super(pID);
	}

	protected abstract void gatherData();
	protected abstract void notifyListeners();

	public void setInput(DirtyWaterStore source){
		myDirtyWaterStore = source;
	}
	
	public DirtyWaterStore getInput(){
		return myDirtyWaterStore;
	}
	
	public float getMax(){
		return myDirtyWaterStore.getCapacity();
	}
	
	/**
	* Returns the name of this module (DirtyWaterStoreSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "DirtyWaterStoreSensor"+getID();
	}
}

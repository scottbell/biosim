package biosim.server.sensor.water;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.water.*;
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
}

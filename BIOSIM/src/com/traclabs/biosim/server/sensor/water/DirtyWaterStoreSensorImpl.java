package biosim.server.sensor.water;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.water.*;
import biosim.idl.framework.*;
import biosim.idl.simulation.water.*;

public abstract class DirtyWaterStoreSensorImpl extends GenericSensorImpl implements DirtyWaterStoreSensorOperations{
	protected DirtyWaterStore myDirtyWaterStore;
	
	public DirtyWaterStoreSensorImpl(int pID, String pName){
		super(pID, pName);
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
	
	public BioModule getInputModule(){
		return (BioModule)(getInput());
	}
}

package biosim.server.sensor.water;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.water.*;
import biosim.idl.framework.*;
import biosim.idl.simulation.water.*;

public abstract class WaterStoreSensorImpl extends GenericSensorImpl implements WaterStoreSensorOperations{
	protected WaterStore myWaterStore;
	
	public WaterStoreSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected abstract void gatherData();
	protected abstract void notifyListeners();

	public void setInput(WaterStore source){
		myWaterStore = source;
	}
	
	public WaterStore getInput(){
		return myWaterStore;
	}
	
	public float getMax(){
		return myWaterStore.getCapacity();
	}
	
	public BioModule getInputModule(){
		return (BioModule)(getInput());
	}
}

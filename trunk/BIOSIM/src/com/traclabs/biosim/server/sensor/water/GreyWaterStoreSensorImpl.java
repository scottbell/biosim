package biosim.server.sensor.water;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.water.*;
import biosim.idl.framework.*;
import biosim.idl.simulation.water.*;

public abstract class GreyWaterStoreSensorImpl extends GenericSensorImpl implements GreyWaterStoreSensorOperations{
	protected GreyWaterStore myGreyWaterStore;
	
	public GreyWaterStoreSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected abstract void gatherData();
	protected abstract void notifyListeners();

	public void setInput(GreyWaterStore source){
		myGreyWaterStore = source;
	}
	
	public GreyWaterStore getInput(){
		return myGreyWaterStore;
	}
	
	public float getMax(){
		return myGreyWaterStore.getCapacity();
	}
	
	public BioModule getInputModule(){
		return (BioModule)(getInput());
	}
}

package biosim.server.sensor.water;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.water.*;
import biosim.idl.framework.*;
import biosim.idl.simulation.water.*;

public abstract class PotableWaterStoreSensorImpl extends GenericSensorImpl implements PotableWaterStoreSensorOperations{
	protected PotableWaterStore myPotableWaterStore;
	
	public PotableWaterStoreSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected abstract void gatherData();
	protected abstract void notifyListeners();

	public void setInput(PotableWaterStore source){
		myPotableWaterStore = source;
	}
	
	public PotableWaterStore getInput(){
		return myPotableWaterStore;
	}
	
	public float getMax(){
		return myPotableWaterStore.getCapacity();
	}
	
	protected BioModule getInputModule(){
		return (BioModule)(getInput());
	}
}

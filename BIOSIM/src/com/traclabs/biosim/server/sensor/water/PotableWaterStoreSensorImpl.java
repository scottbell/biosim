package biosim.server.sensor.water;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.water.*;
import biosim.idl.simulation.water.*;

public abstract class PotableWaterStoreSensorImpl extends GenericSensorImpl implements PotableWaterStoreSensorOperations{
	protected PotableWaterStore myPotableWaterStore;
	
	public PotableWaterStoreSensorImpl(int pID){
		super(pID);
	}

	protected abstract void gatherData();
	protected abstract void notifyListeners();

	public void setInput(PotableWaterStore source){
		myPotableWaterStore = source;
	}
	
	public PotableWaterStore getInput(){
		return myPotableWaterStore;
	}
}

package biosim.server.sensor.water;

import biosim.idl.framework.BioModule;
import biosim.idl.sensor.water.PotableWaterStoreSensorOperations;
import biosim.idl.simulation.water.PotableWaterStore;
import biosim.server.sensor.framework.GenericSensorImpl;

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
	
	public BioModule getInputModule(){
		return (BioModule)(getInput());
	}
}

package biosim.server.sensor.framework;

import biosim.idl.framework.*;
import biosim.idl.simulation.framework.*;
import biosim.idl.sensor.framework.*;

public abstract class StoreSensorImpl extends GenericSensorImpl implements StoreSensorOperations{
	protected Store myStore;
	
	public StoreSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected abstract void gatherData();
	protected abstract void notifyListeners();

	public void setInput(Store source){
		myStore = source;
	}
	
	public Store getInput(){
		return myStore;
	}
	
	public float getMax(){
		return myStore.getCapacity();
	}
	
	protected BioModule getInputModule(){
		return (BioModule)(getInput());
	}
}

package biosim.server.sensor.waste;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.waste.*;
import biosim.idl.framework.*;
import biosim.idl.simulation.waste.*;

public abstract class DryWasteStoreSensorImpl extends GenericSensorImpl implements DryWasteStoreSensorOperations{
	protected DryWasteStore myDryWasteStore;
	
	public DryWasteStoreSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected abstract void gatherData();
	protected abstract void notifyListeners();

	public void setInput(DryWasteStore source){
		myDryWasteStore = source;
	}
	
	public DryWasteStore getInput(){
		return myDryWasteStore;
	}
	
	public float getMax(){
		return myDryWasteStore.getCapacity();
	}
	
	protected BioModule getInputModule(){
		return (BioModule)(getInput());
	}
}

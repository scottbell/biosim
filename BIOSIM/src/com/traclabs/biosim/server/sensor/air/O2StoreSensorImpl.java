package biosim.server.sensor.air;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.air.*;
import biosim.idl.framework.*;
import biosim.idl.simulation.air.*;

public abstract class O2StoreSensorImpl extends GenericSensorImpl implements O2StoreSensorOperations{
	private O2Store myO2Store;
	
	public O2StoreSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected abstract void gatherData();
	protected abstract void notifyListeners();

	public void setInput(O2Store source){
		myO2Store = source;
	}
	
	public O2Store getInput(){
		return myO2Store;
	}
	
	public float getMax(){
		return myO2Store.getCapacity();
	}
	
	protected BioModule getInputModule(){
		return (BioModule)(getInput());
	}
}

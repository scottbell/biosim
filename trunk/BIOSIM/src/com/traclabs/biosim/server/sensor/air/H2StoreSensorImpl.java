package biosim.server.sensor.air;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.air.*;
import biosim.idl.framework.*;
import biosim.idl.simulation.air.*;

public abstract class H2StoreSensorImpl extends GenericSensorImpl implements H2StoreSensorOperations{
	private H2Store myH2Store;
	
	public H2StoreSensorImpl(int pID){
		super(pID);
	}

	protected abstract void gatherData();
	protected abstract void notifyListeners();

	public void setInput(H2Store source){
		myH2Store = source;
	}
	
	public H2Store getInput(){
		return myH2Store;
	}
	
	public float getMax(){
		return myH2Store.getCapacity();
	}
	
	/**
	* Returns the name of this module (H2StoreSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "H2StoreSensor"+getID();
	}
	
	protected BioModule getInputModule(){
		return (BioModule)(getInput());
	}
}

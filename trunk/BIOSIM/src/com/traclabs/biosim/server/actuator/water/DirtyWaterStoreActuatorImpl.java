package biosim.server.actuator.water;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.water.*;
import biosim.idl.simulation.water.*;

public abstract class DirtyWaterStoreActuatorImpl extends GenericActuatorImpl implements DirtyWaterStoreActuatorOperations{
	protected DirtyWaterStore myDirtyWaterStore;
	
	public DirtyWaterStoreActuatorImpl(int pID){
		super(pID);
	}

	protected abstract void processData();
	protected abstract void notifyListeners();

	public void setOutput(DirtyWaterStore source){
		myDirtyWaterStore = source;
	}
	
	public DirtyWaterStore getOutput(){
		return myDirtyWaterStore;
	}
	
	/**
	* Returns the name of this module (DirtyWaterStoreActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "DirtyWaterStoreActuator"+getID();
	}
}

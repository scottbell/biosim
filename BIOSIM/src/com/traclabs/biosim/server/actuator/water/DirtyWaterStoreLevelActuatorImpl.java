package biosim.server.actuator.water;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.water.*;
import biosim.idl.simulation.water.*;

public class DirtyWaterStoreLevelActuatorImpl extends DirtyWaterStoreActuatorImpl implements DirtyWaterStoreLevelActuatorOperations{
	public DirtyWaterStoreLevelActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setLevel(myFilteredValue);
	}
	
	protected void notifyListeners(){
	}
	
	/**
	* Returns the name of this module (DirtyWaterStoreLevelActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "DirtyWaterStoreLevelActuator"+getID();
	}
}

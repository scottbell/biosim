package biosim.server.actuator.air;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.air.*;
import biosim.idl.simulation.air.*;

public class O2StoreLevelActuatorImpl extends O2StoreActuatorImpl implements O2StoreLevelActuatorOperations{
	public O2StoreLevelActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setLevel(myFilteredValue);
	}
	protected void notifyListeners(){
		//Does nothing right now..
	}
	
	/**
	* Returns the name of this module (O2StoreLevelActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "O2StoreLevelActuator"+getID();
	}
}

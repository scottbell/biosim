package biosim.server.actuator.power;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.power.*;
import biosim.idl.simulation.power.*;

public class PowerStoreLevelActuatorImpl extends PowerStoreActuatorImpl implements PowerStoreLevelActuatorOperations{
	public PowerStoreLevelActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setLevel(myFilteredValue);
	}
	
	protected void notifyListeners(){
	}
	
	/**
	* Returns the name of this module (PowerStoreLevelActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "PowerStoreLevelActuator"+getID();
	}
}

package biosim.server.actuator.air;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.air.*;
import biosim.idl.framework.*;

public class CO2StoreLevelActuatorImpl extends CO2StoreActuatorImpl implements CO2StoreLevelActuatorOperations{
	public CO2StoreLevelActuatorImpl(int pID){
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
	* Returns the name of this module (CO2StoreLevelActuatorImpl)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "CO2StoreLevelActuator"+getID();
	}
}

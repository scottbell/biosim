package biosim.server.actuator.environment;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.environment.*;
import biosim.idl.simulation.environment.*;

public class O2AirMolesActuatorImpl extends EnvironmentActuatorImpl implements O2AirMolesActuatorOperations{
	public O2AirMolesActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setO2Moles(myFilteredValue);
	}

	protected void notifyListeners(){
	}
	
	/**
	* Returns the name of this module (O2AirMolesActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "O2AirMolesActuator"+getID();
	}
}

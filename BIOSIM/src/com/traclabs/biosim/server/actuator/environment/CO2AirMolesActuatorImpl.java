package biosim.server.actuator.environment;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.environment.*;
import biosim.idl.simulation.environment.*;

public class CO2AirMolesActuatorImpl extends EnvironmentActuatorImpl implements CO2AirMolesActuatorOperations{
	public CO2AirMolesActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setCO2Moles(myFilteredValue);
	}
	
	protected void notifyListeners(){
	}
	
	/**
	* Returns the name of this module (CO2AirMolesActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "CO2AirMolesActuator"+getID();
	}
}

package biosim.server.actuator.environment;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.environment.*;
import biosim.idl.simulation.environment.*;

public class OtherAirMolesActuatorImpl extends EnvironmentActuatorImpl implements OtherAirMolesActuatorOperations{
	public OtherAirMolesActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setOtherMoles(myFilteredValue);
	}
	
	protected void notifyListeners(){
	}
	
	/**
	* Returns the name of this module (OtherAirMolesActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "OtherAirMolesActuator"+getID();
	}
}

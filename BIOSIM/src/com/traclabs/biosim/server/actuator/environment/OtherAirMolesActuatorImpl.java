package biosim.server.actuator.environment;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.environment.*;
import biosim.idl.simulation.environment.*;

public class OtherAirLevelActuatorImpl extends EnvironmentActuatorImpl implements OtherAirLevelActuatorOperations{
	public OtherAirLevelActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setOtherLevel(myFilteredValue);
	}
	
	protected void notifyListeners(){
	}
	
	/**
	* Returns the name of this module (OtherAirLevelActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "OtherAirLevelActuator"+getID();
	}
}

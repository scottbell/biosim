package biosim.server.actuator.environment;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.environment.*;
import biosim.idl.simulation.environment.*;

public class CO2AirLevelActuatorImpl extends EnvironmentActuatorImpl implements CO2AirLevelActuatorOperations{
	public CO2AirLevelActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setCO2Level(myFilteredValue);
	}
	
	protected void notifyListeners(){
	}
	
	/**
	* Returns the name of this module (CO2AirLevelActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "CO2AirLevelActuator"+getID();
	}
}

package biosim.server.actuator.environment;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.environment.*;
import biosim.idl.simulation.environment.*;

public class O2AirLevelActuatorImpl extends EnvironmentActuatorImpl implements O2AirLevelActuatorOperations{
	public O2AirLevelActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setO2Level(myFilteredValue);
	}

	protected void notifyListeners(){
	}
	
	/**
	* Returns the name of this module (O2AirLevelActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "O2AirLevelActuator"+getID();
	}
}

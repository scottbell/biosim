package biosim.server.actuator.environment;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.environment.*;
import biosim.idl.simulation.environment.*;

public abstract class EnvironmentActuatorImpl extends GenericActuatorImpl implements EnvironmentActuatorOperations{
	protected SimEnvironment myEnvironment;
	
	public EnvironmentActuatorImpl(int pID){
		super(pID);
	}
	
	public void setOutput(SimEnvironment source){
		myEnvironment = source;
	}
	
	public SimEnvironment getOutput(){
		return myEnvironment;
	}
	
	/**
	* Returns the name of this module (EnvironmentActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "EnvironmentActuator"+getID();
	}
}

package biosim.server.actuator.environment;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.environment.*;
import biosim.idl.simulation.environment.*;

public abstract class EnvironmentActuatorImpl extends GenericActuatorImpl implements EnvironmentActuatorOperations{
	protected SimEnvironment myEnvironment;
	
	public EnvironmentActuatorImpl(int pID, String pName){
		super(pID, pName);
	}
	
	public void setOutput(SimEnvironment source){
		myEnvironment = source;
	}
	
	public SimEnvironment getOutput(){
		return myEnvironment;
	}
}

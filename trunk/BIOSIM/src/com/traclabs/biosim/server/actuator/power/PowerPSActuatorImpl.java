package biosim.server.actuator.power;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.power.*;
import biosim.idl.framework.*;
import biosim.idl.simulation.power.*;

public abstract class PowerPSActuatorImpl extends GenericActuatorImpl implements PowerPSActuatorOperations{
	protected PowerPS myPowerPS;
	
	public PowerPSActuatorImpl(int pID){
		super(pID);
	}

	protected abstract void processData();
	protected abstract void notifyListeners();

	public void setOutput(PowerPS source){
		myPowerPS = source;
	}
	
	public PowerPS getOutput(){
		return myPowerPS;
	}
	
	/**
	* Returns the name of this module (PowerPSActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "PowerPSActuator"+getID();
	}
}

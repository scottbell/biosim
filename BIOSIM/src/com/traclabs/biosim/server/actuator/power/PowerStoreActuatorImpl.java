package biosim.server.actuator.power;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.power.*;
import biosim.idl.framework.*;
import biosim.idl.simulation.power.*;

public abstract class PowerStoreActuatorImpl extends GenericActuatorImpl implements PowerStoreActuatorOperations{
	protected PowerStore myPowerStore;
	
	public PowerStoreActuatorImpl(int pID){
		super(pID);
	}

	protected abstract void processData();
	protected abstract void notifyListeners();

	public void setOutput(PowerStore source){
		myPowerStore = source;
	}
	
	public PowerStore getOutput(){
		return myPowerStore;
	}
	
	/**
	* Returns the name of this module (PowerStoreActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "PowerStoreActuator"+getID();
	}
}

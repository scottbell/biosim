package biosim.server.actuator.power;

import biosim.idl.actuator.power.PowerPSActuatorOperations;
import biosim.idl.simulation.power.PowerPS;
import biosim.server.actuator.framework.GenericActuatorImpl;

public abstract class PowerPSActuatorImpl extends GenericActuatorImpl implements PowerPSActuatorOperations{
	protected PowerPS myPowerPS;
	
	public PowerPSActuatorImpl(int pID, String pName){
		super(pID, pName);
	}

	public void setOutput(PowerPS source){
		myPowerPS = source;
	}
	
	public PowerPS getOutput(){
		return myPowerPS;
	}
}

package biosim.server.actuator.air;

import biosim.idl.actuator.air.AirRSActuatorOperations;
import biosim.idl.simulation.air.AirRS;
import biosim.server.actuator.framework.GenericActuatorImpl;

public abstract class AirRSActuatorImpl extends GenericActuatorImpl implements AirRSActuatorOperations{
	protected AirRS myAirRS;
	
	public AirRSActuatorImpl(int pID, String pName){
		super(pID, pName);
	}
	
	public void setOutput(AirRS source){
		myAirRS = source;
	}
	
	public AirRS getOutput(){
		return myAirRS;
	}
}

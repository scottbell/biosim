package biosim.server.actuator.air;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.air.*;
import biosim.idl.simulation.air.*;
import biosim.idl.framework.*;

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

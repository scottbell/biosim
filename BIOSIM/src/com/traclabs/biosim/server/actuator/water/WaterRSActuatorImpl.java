package biosim.server.actuator.water;

import biosim.idl.actuator.water.WaterRSActuatorOperations;
import biosim.idl.simulation.water.WaterRS;
import biosim.server.actuator.framework.GenericActuatorImpl;

public abstract class WaterRSActuatorImpl extends GenericActuatorImpl implements WaterRSActuatorOperations{
	protected WaterRS myWaterRS;
	
	public WaterRSActuatorImpl(int pID, String pName){
		super(pID, pName);
	}

	public void setOutput(WaterRS source){
		myWaterRS = source;
	}
	
	public WaterRS getOutput(){
		return myWaterRS;
	}
}

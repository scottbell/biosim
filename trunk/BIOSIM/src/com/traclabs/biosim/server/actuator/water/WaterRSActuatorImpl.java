package biosim.server.actuator.water;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.water.*;
import biosim.idl.simulation.water.*;

public abstract class WaterRSActuatorImpl extends GenericActuatorImpl implements WaterRSActuatorOperations{
	protected WaterRS myWaterRS;
	
	public WaterRSActuatorImpl(int pID){
		super(pID);
	}

	protected abstract void processData();
	protected abstract void notifyListeners();

	public void setOutput(WaterRS source){
		myWaterRS = source;
	}
	
	public WaterRS getOutput(){
		return myWaterRS;
	}
	
	/**
	* Returns the name of this module (WaterRSActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "WaterRSActuator"+getID();
	}
}

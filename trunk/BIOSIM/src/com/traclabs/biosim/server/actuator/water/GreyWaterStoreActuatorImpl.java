package biosim.server.actuator.water;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.water.*;
import biosim.idl.framework.*;
import biosim.idl.simulation.water.*;

public abstract class GreyWaterStoreActuatorImpl extends GenericActuatorImpl implements GreyWaterStoreActuatorOperations{
	protected GreyWaterStore myGreyWaterStore;
	
	public GreyWaterStoreActuatorImpl(int pID){
		super(pID);
	}

	protected abstract void processData();
	protected abstract void notifyListeners();

	public void setOutput(GreyWaterStore source){
		myGreyWaterStore = source;
	}
	
	public GreyWaterStore getOutput(){
		return myGreyWaterStore;
	}
	
	/**
	* Returns the name of this module (GreyWaterStoreActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "GreyWaterStoreActuator"+getID();
	}
}

package biosim.server.actuator.water;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.water.*;
import biosim.idl.simulation.water.*;

public abstract class PotableWaterStoreActuatorImpl extends GenericActuatorImpl implements PotableWaterStoreActuatorOperations{
	protected PotableWaterStore myPotableWaterStore;
	
	public PotableWaterStoreActuatorImpl(int pID){
		super(pID);
	}

	protected abstract void processData();
	protected abstract void notifyListeners();

	public void setOutput(PotableWaterStore source){
		myPotableWaterStore = source;
	}
	
	public PotableWaterStore getOutput(){
		return myPotableWaterStore;
	}
	
	/**
	* Returns the name of this module (PotableWaterStoreActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "PotableWaterStoreActuator"+getID();
	}
}

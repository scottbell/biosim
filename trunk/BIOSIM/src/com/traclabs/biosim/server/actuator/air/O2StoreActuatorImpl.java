package biosim.server.actuator.air;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.air.*;
import biosim.idl.framework.*;
import biosim.idl.simulation.air.*;

public abstract class O2StoreActuatorImpl extends GenericActuatorImpl implements O2StoreActuatorOperations{
	private O2Store myO2Store;
	
	public O2StoreActuatorImpl(int pID){
		super(pID);
	}

	protected abstract void processData();
	protected abstract void notifyListeners();

	public void setOutput(O2Store source){
		myO2Store = source;
	}
	
	public O2Store getOutput(){
		return myO2Store;
	}
	
	/**
	* Returns the name of this module (O2StoreActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "O2StoreActuator"+getID();
	}
}

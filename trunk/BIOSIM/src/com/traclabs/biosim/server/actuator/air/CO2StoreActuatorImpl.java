package biosim.server.actuator.air;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.air.*;
import biosim.idl.framework.*;
import biosim.idl.simulation.air.*;

public abstract class CO2StoreActuatorImpl extends GenericActuatorImpl implements CO2StoreActuatorOperations{
	private CO2Store myCO2Store;
	
	public CO2StoreActuatorImpl(int pID){
		super(pID);
	}

	protected abstract void processData();
	protected abstract void notifyListeners();

	public void setOutput(CO2Store source){
		myCO2Store = source;
	}
	
	public CO2Store getOutput(){
		return myCO2Store;
	}
	
	/**
	* Returns the name of this module (CO2StoreActuatorImpl)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "CO2StoreActuator"+getID();
	}
}

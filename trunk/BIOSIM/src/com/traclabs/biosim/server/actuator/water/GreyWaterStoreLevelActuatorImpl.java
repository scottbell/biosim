package biosim.server.actuator.water;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.water.*;
import biosim.idl.framework.*;

public class GreyWaterStoreLevelActuatorImpl extends GreyWaterStoreActuatorImpl implements GreyWaterStoreLevelActuatorOperations{
	public GreyWaterStoreLevelActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setLevel(myFilteredValue);
	}
	
	protected void notifyListeners(){
	}
	
	/**
	* Returns the name of this module (GreyWaterStoreLevelActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "GreyWaterStoreLevelActuator"+getID();
	}
}

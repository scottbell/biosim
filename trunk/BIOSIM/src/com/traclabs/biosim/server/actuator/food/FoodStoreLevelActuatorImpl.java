package biosim.server.actuator.food;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.food.*;
import biosim.idl.framework.*;

public class FoodStoreLevelActuatorImpl extends FoodStoreActuatorImpl implements FoodStoreLevelActuatorOperations{
	public FoodStoreLevelActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setLevel(myFilteredValue);
	}
	
	protected void notifyListeners(){
	}
	
	/**
	* Returns the name of this module (FoodStoreLevelActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "FoodStoreLevelActuator"+getID();
	}
}

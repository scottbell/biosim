package biosim.server.actuator.food;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.food.*;
import biosim.idl.simulation.food.*;

public abstract class FoodStoreActuatorImpl extends GenericActuatorImpl implements FoodStoreActuatorOperations{
	protected FoodStore myFoodStore;
	
	public FoodStoreActuatorImpl(int pID){
		super(pID);
	}

	protected abstract void processData();
	protected abstract void notifyListeners();

	public void setOutput(FoodStore source){
		myFoodStore = source;
	}
	
	public FoodStore getOutput(){
		return myFoodStore;
	}
	
	/**
	* Returns the name of this module (FoodStoreActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "FoodStoreActuator"+getID();
	}
}

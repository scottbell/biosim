package biosim.server.sensor.food;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.food.*;
import biosim.idl.framework.*;
import biosim.idl.simulation.food.*;

public abstract class FoodStoreSensorImpl extends GenericSensorImpl implements FoodStoreSensorOperations{
	protected FoodStore myFoodStore;
	
	public FoodStoreSensorImpl(int pID){
		super(pID);
	}

	protected abstract void gatherData();
	protected abstract void notifyListeners();

	public void setInput(FoodStore source){
		myFoodStore = source;
	}
	
	public FoodStore getInput(){
		return myFoodStore;
	}
	
	/**
	* Returns the name of this module (FoodStoreSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "FoodStoreSensor"+getID();
	}
}

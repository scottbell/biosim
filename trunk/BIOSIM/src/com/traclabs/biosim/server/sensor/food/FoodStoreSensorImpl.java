package biosim.server.sensor.food;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.food.*;
import biosim.idl.framework.*;
import biosim.idl.simulation.food.*;

public abstract class FoodStoreSensorImpl extends GenericSensorImpl implements FoodStoreSensorOperations{
	protected FoodStore myFoodStore;
	
	public FoodStoreSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected abstract void gatherData();
	protected abstract void notifyListeners();

	public void setInput(FoodStore source){
		myFoodStore = source;
	}
	
	public FoodStore getInput(){
		return myFoodStore;
	}
	
	public float getMax(){
		return myFoodStore.getLevel();
	}
	
	protected BioModule getInputModule(){
		return (BioModule)(getInput());
	}
}

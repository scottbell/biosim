package biosim.server.sensor.food;

import biosim.idl.framework.BioModule;
import biosim.idl.sensor.food.FoodStoreSensorOperations;
import biosim.idl.simulation.food.FoodStore;
import biosim.server.sensor.framework.GenericSensorImpl;

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
	
	public BioModule getInputModule(){
		return (BioModule)(getInput());
	}
}

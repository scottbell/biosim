package biosim.server.sensor.food;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.food.*;
import biosim.idl.simulation.food.*;

public abstract class FoodProcessorSensorImpl extends GenericSensorImpl implements FoodProcessorSensorOperations{
	protected FoodProcessor myFoodProcessor;
	
	public FoodProcessorSensorImpl(int pID){
		super(pID);
	}

	protected abstract void gatherData();
	protected abstract void notifyListeners();

	public void setInput(FoodProcessor source){
		myFoodProcessor = source;
	}
	
	public FoodProcessor getInput(){
		return myFoodProcessor;
	}
}

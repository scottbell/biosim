package biosim.server.sensor.food;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.food.*;
import biosim.idl.framework.*;
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
	
	/**
	* Returns the name of this module (FoodProcessorSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "FoodProcessorSensor"+getID();
	}
	
	protected BioModule getInputModule(){
		return (BioModule)(getInput());
	}
}

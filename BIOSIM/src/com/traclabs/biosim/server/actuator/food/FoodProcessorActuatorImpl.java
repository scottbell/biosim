package biosim.server.actuator.food;

import biosim.idl.actuator.food.FoodProcessorActuatorOperations;
import biosim.idl.simulation.food.FoodProcessor;
import biosim.server.actuator.framework.GenericActuatorImpl;

public abstract class FoodProcessorActuatorImpl extends GenericActuatorImpl implements FoodProcessorActuatorOperations{
	protected FoodProcessor myFoodProcessor;
	
	public FoodProcessorActuatorImpl(int pID, String pName){
		super(pID, pName);
	}
	
	public void setOutput(FoodProcessor source){
		myFoodProcessor = source;
	}
	
	public FoodProcessor getOutput(){
		return myFoodProcessor;
	}
}

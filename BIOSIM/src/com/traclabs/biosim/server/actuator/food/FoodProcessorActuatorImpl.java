package biosim.server.actuator.food;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.food.*;
import biosim.idl.framework.*;
import biosim.idl.simulation.food.*;

public abstract class FoodProcessorActuatorImpl extends GenericActuatorImpl implements FoodProcessorActuatorOperations{
	protected FoodProcessor myFoodProcessor;
	
	public FoodProcessorActuatorImpl(int pID){
		super(pID);
	}
	
	public void setOutput(FoodProcessor source){
		myFoodProcessor = source;
	}
	
	public FoodProcessor getOutput(){
		return myFoodProcessor;
	}
	
	/**
	* Returns the name of this module (FoodProcessorActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "FoodProcessorActuator"+getID();
	}
}

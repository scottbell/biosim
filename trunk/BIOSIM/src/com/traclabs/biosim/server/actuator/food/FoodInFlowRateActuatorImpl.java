package biosim.server.actuator.food;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.food.*;
import biosim.idl.framework.*;

public class FoodInFlowRateActuatorImpl extends GenericActuatorImpl implements FoodInFlowRateActuatorOperations{
	private FoodConsumer myConsumer;
	private int myIndex;
	
	public FoodInFlowRateActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setFoodInputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(FoodConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	protected BioModule getModuleOutput(){
		return (BioModule)(myConsumer);
	}
	
	public FoodConsumer getOutput(){
		return myConsumer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	public float getMax(){
		return myConsumer.getFoodInputMaxFlowRate(myIndex);
	}
	
	/**
	* Returns the name of this module (FoodInFlowRateActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "FoodInFlowRateActuator"+getID();
	}
}

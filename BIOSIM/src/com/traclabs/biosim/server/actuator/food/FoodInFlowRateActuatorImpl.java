package biosim.server.actuator.food;

import biosim.idl.actuator.food.FoodInFlowRateActuatorOperations;
import biosim.idl.framework.BioModule;
import biosim.idl.framework.FoodConsumer;
import biosim.server.actuator.framework.GenericActuatorImpl;

public class FoodInFlowRateActuatorImpl extends GenericActuatorImpl implements FoodInFlowRateActuatorOperations{
	private FoodConsumer myConsumer;
	private int myIndex;
	
	public FoodInFlowRateActuatorImpl(int pID, String pName){
		super(pID, pName);
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
	
	public BioModule getOutputModule(){
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
}

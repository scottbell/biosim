package biosim.server.sensor.food;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.food.*;
import biosim.idl.simulation.food.*;

public class FoodInFlowRateSensorImpl extends GenericSensorImpl implements FoodInFlowRateSensorOperations{
	private FoodConsumer myConsumer;
	private int myIndex;
	
	public FoodInFlowRateSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		double preFilteredValue = getInput().getFoodInputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(FoodConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public FoodConsumer getInput(){
		return myConsumer;
	}
	
	public int getIndex(){
		return myIndex;
	}
}

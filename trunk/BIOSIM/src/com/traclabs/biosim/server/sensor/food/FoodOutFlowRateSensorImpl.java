package biosim.server.sensor.food;

import biosim.idl.framework.BioModule;
import biosim.idl.framework.FoodProducer;
import biosim.idl.sensor.food.FoodOutFlowRateSensorOperations;
import biosim.server.sensor.framework.GenericSensorImpl;

public class FoodOutFlowRateSensorImpl extends GenericSensorImpl implements FoodOutFlowRateSensorOperations{
	private FoodProducer myProducer;
	private int myIndex;
	
	public FoodOutFlowRateSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getFoodOutputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(FoodProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public float getMax(){
		return myProducer.getFoodOutputMaxFlowRate(myIndex);
	}
	
	public FoodProducer getInput(){
		return myProducer;
	}
	
	public BioModule getInputModule(){
		return (BioModule)(myProducer);
	}
	
	public int getIndex(){
		return myIndex;
	}
}

package biosim.server.sensor.food;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.food.*;
import biosim.idl.simulation.food.*;

public class FoodOutFlowRateSensorImpl extends GenericSensorImpl implements FoodOutFlowRateSensorOperations{
	private FoodProducer myProducer;
	private int myIndex;
	
	public FoodOutFlowRateSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		double preFilteredValue = getInput().getFoodOutputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(FoodProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public FoodProducer getInput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	/**
	* Returns the name of this module (FoodOutFlowRateSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "FoodOutFlowRateSensor"+getID();
	}
}

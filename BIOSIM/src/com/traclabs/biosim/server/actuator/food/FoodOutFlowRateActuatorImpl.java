package com.traclabs.biosim.server.actuator.food;

import com.traclabs.biosim.idl.actuator.food.FoodOutFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.framework.FoodProducer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class FoodOutFlowRateActuatorImpl extends GenericActuatorImpl implements FoodOutFlowRateActuatorOperations{
	private FoodProducer myProducer;
	private int myIndex;
	
	public FoodOutFlowRateActuatorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setFoodOutputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(FoodProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public BioModule getOutputModule(){
		return (BioModule)(myProducer);
	}
	
	public FoodProducer getOutput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	public float getMax(){
		return myProducer.getFoodOutputMaxFlowRate(myIndex);
	}
}

package biosim.server.actuator.water;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.water.*;
import biosim.idl.framework.*;

public class PotableWaterOutFlowRateActuatorImpl extends GenericActuatorImpl implements PotableWaterOutFlowRateActuatorOperations{
	private PotableWaterProducer myProducer;
	private int myIndex;
	
	public PotableWaterOutFlowRateActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setPotableWaterOutputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(PotableWaterProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public PotableWaterProducer getOutput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	public float getMax(){
		return myProducer.getPotableWaterOutputMaxFlowRate(myIndex);
	}
	
	/**
	* Returns the name of this module (PotableWaterOutFlowRateActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "PotableWaterOutFlowRateActuator"+getID();
	}
}

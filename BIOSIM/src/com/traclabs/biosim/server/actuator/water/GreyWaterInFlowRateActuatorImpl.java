package biosim.server.actuator.water;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.water.*;
import biosim.idl.framework.*;

public class GreyWaterInFlowRateActuatorImpl extends GenericActuatorImpl implements GreyWaterInFlowRateActuatorOperations{
	private GreyWaterConsumer myConsumer;
	private int myIndex;
	
	public GreyWaterInFlowRateActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setGreyWaterInputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(GreyWaterConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	protected BioModule getModuleOuput(){
		return (BioModule)(myConsumer);
	}
	
	public GreyWaterConsumer getOutput(){
		return myConsumer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	public float getMax(){
		return myConsumer.getGreyWaterInputMaxFlowRate(myIndex);
	}
	
	/**
	* Returns the name of this module (GreyWaterInFlowRateActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "GreyWaterInFlowRateActuator"+getID();
	}
}

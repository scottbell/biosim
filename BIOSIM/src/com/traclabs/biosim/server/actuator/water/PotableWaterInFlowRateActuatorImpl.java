package biosim.server.actuator.water;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.water.*;
import biosim.idl.framework.*;

public class PotableWaterInFlowRateActuatorImpl extends GenericActuatorImpl implements PotableWaterInFlowRateActuatorOperations{
	private PotableWaterConsumer myConsumer;
	private int myIndex;
	
	public PotableWaterInFlowRateActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setPotableWaterInputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(PotableWaterConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	protected BioModule getOutputModule(){
		return (BioModule)(myConsumer);
	}
	
	public PotableWaterConsumer getOutput(){
		return myConsumer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	public float getMax(){
		return myConsumer.getPotableWaterInputMaxFlowRate(myIndex);
	}
	
	/**
	* Returns the name of this module (PotableWaterInFlowRateActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "PotableWaterInFlowRateActuator"+getID();
	}
}

package biosim.server.actuator.air;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.air.*;
import biosim.idl.framework.*;

public class H2InFlowRateActuatorImpl extends GenericActuatorImpl implements H2InFlowRateActuatorOperations{
	private H2Consumer myConsumer;
	private int myIndex;
	
	public H2InFlowRateActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setH2InputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(H2Consumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	protected BioModule getOutputModule(){
		return (BioModule)(myConsumer);
	}
	
	public H2Consumer getOutput(){
		return myConsumer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	public float getMax(){
		return myConsumer.getH2InputMaxFlowRate(myIndex);
	}
	
	/**
	* Returns the name of this module (H2InFlowRateActuatorImpl)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "H2InFlowRateActuator"+getID();
	}
}

package biosim.server.actuator.air;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.air.*;
import biosim.idl.simulation.air.*;

public class O2InFlowRateActuatorImpl extends GenericActuatorImpl implements O2InFlowRateActuatorOperations{
	private O2Consumer myConsumer;
	private int myIndex;
	
	public O2InFlowRateActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setO2InputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(O2Consumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public O2Consumer getOutput(){
		return myConsumer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	/**
	* Returns the name of this module (O2InFlowRateActuatorImpl)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "O2InFlowRateActuator"+getID();
	}
}

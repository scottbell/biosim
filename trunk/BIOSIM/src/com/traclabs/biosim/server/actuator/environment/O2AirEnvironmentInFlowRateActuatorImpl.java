package biosim.server.actuator.environment;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.environment.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.framework.*;

public class O2AirEnvironmentInFlowRateActuatorImpl extends GenericActuatorImpl implements O2AirEnvironmentInFlowRateActuatorOperations{
	private O2AirConsumer myConsumer;
	private int myIndex;
	
	public O2AirEnvironmentInFlowRateActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setO2AirEnvironmentInputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(O2AirConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public O2AirConsumer getOutput(){
		return myConsumer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	/**
	* Returns the name of this module (O2AirEnvironmentInFlowRateActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "O2AirEnvironmentInFlowRateActuator"+getID();
	}
}

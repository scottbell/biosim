package biosim.server.actuator.environment;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.environment.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.framework.*;

public class AirInFlowRateActuatorImpl extends GenericActuatorImpl implements AirInFlowRateActuatorOperations{
	private AirConsumer myConsumer;
	private int myIndex;
	
	public AirInFlowRateActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setAirInputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(AirConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public AirConsumer getOutput(){
		return myConsumer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	/**
	* Returns the name of this module (AirInFlowRateActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "AirInFlowRateActuator"+getID();
	}
}

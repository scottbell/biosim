package biosim.server.actuator.power;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.power.*;
import biosim.idl.simulation.power.*;

public class PowerInFlowRateActuatorImpl extends GenericActuatorImpl implements PowerInFlowRateActuatorOperations{
	private PowerConsumer myConsumer;
	private int myIndex;
	
	public PowerInFlowRateActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setPowerInputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(PowerConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public PowerConsumer getOutput(){
		return myConsumer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	/**
	* Returns the name of this module (PowerInFlowRateActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "PowerInFlowRateActuator"+getID();
	}
}

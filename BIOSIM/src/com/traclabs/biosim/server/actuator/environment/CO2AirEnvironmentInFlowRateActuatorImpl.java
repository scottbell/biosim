package biosim.server.actuator.environment;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.environment.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.framework.*;

public class CO2AirEnvironmentInFlowRateActuatorImpl extends GenericActuatorImpl implements CO2AirEnvironmentInFlowRateActuatorOperations{
	private CO2AirConsumer myConsumer;
	private int myIndex;
	
	public CO2AirEnvironmentInFlowRateActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setCO2AirEnvironmentInputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(CO2AirConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public CO2AirConsumer getOutput(){
		return myConsumer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	public float getMax(){
		return myConsumer.getCO2AirEnvironmentInputMaxFlowRate(myIndex);
	}
	
	/**
	* Returns the name of this module (CO2AirEnvironmentInFlowRateActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "CO2AirEnvironmentInFlowRateActuator"+getID();
	}
}

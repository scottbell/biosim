package biosim.server.actuator.air;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.air.*;
import biosim.idl.simulation.air.*;

public class CO2InFlowRateActuatorImpl extends GenericActuatorImpl implements CO2InFlowRateActuatorOperations{
	private CO2Consumer myConsumer;
	private int myIndex;
	
	public CO2InFlowRateActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setCO2InputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(CO2Consumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public CO2Consumer getOutput(){
		return myConsumer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	/**
	* Returns the name of this module (CO2InFlowRateActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "CO2InFlowRateActuator"+getID();
	}
}

package biosim.server.actuator.environment;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.environment.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.framework.*;

public class O2AirEnvironmentOutFlowRateActuatorImpl extends GenericActuatorImpl implements O2AirEnvironmentOutFlowRateActuatorOperations{
	private O2AirProducer myProducer;
	private int myIndex;
	
	public O2AirEnvironmentOutFlowRateActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setO2AirEnvironmentOutputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(O2AirProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public O2AirProducer getOutput(){
		return myProducer;
	}
	
	public float getMax(){
		return myProducer.getO2AirEnvironmentOutputMaxFlowRate(myIndex);
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	/**
	* Returns the name of this module (O2AirEnvironmentOutFlowRateActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "O2AirEnvironmentOutFlowRateActuator"+getID();
	}
}

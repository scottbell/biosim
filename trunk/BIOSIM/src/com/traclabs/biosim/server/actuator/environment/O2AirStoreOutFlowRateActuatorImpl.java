package biosim.server.actuator.environment;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.environment.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.framework.*;

public class O2AirStoreOutFlowRateActuatorImpl extends GenericActuatorImpl implements O2AirStoreOutFlowRateActuatorOperations{
	private O2AirProducer myProducer;
	private int myIndex;
	
	public O2AirStoreOutFlowRateActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setO2AirStoreOutputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(O2AirProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	protected BioModule getModuleOutput(){
		return (BioModule)(myProducer);
	}
	
	public float getMax(){
		return myProducer.getO2AirStoreOutputMaxFlowRate(myIndex);
	}
	
	public O2AirProducer getOutput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	/**
	* Returns the name of this module (O2AirStoreOutFlowRateActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "O2AirStoreOutFlowRateActuator"+getID();
	}
}

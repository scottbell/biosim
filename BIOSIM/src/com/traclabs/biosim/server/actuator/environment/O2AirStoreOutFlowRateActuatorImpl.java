package biosim.server.actuator.environment;

import biosim.idl.actuator.environment.O2AirStoreOutFlowRateActuatorOperations;
import biosim.idl.framework.BioModule;
import biosim.idl.framework.O2AirProducer;
import biosim.server.actuator.framework.GenericActuatorImpl;

public class O2AirStoreOutFlowRateActuatorImpl extends GenericActuatorImpl implements O2AirStoreOutFlowRateActuatorOperations{
	private O2AirProducer myProducer;
	private int myIndex;
	
	public O2AirStoreOutFlowRateActuatorImpl(int pID, String pName){
		super(pID, pName);
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
	
	public BioModule getOutputModule(){
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
}

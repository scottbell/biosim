package biosim.server.actuator.environment;

import biosim.idl.actuator.environment.CO2AirStoreOutFlowRateActuatorOperations;
import biosim.idl.framework.BioModule;
import biosim.idl.framework.CO2AirProducer;
import biosim.server.actuator.framework.GenericActuatorImpl;

public class CO2AirStoreOutFlowRateActuatorImpl extends GenericActuatorImpl implements CO2AirStoreOutFlowRateActuatorOperations{
	private CO2AirProducer myProducer;
	private int myIndex;
	
	public CO2AirStoreOutFlowRateActuatorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setCO2AirStoreOutputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(CO2AirProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public BioModule getOutputModule(){
		return (BioModule)(myProducer);
	}
	
	public float getMax(){
		return myProducer.getCO2AirStoreOutputMaxFlowRate(myIndex);
	}
	
	public CO2AirProducer getOutput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
}

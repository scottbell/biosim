package biosim.server.actuator.environment;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.environment.*;
import biosim.idl.framework.*;

public class AirOutFlowRateActuatorImpl extends GenericActuatorImpl implements AirOutFlowRateActuatorOperations{
	private AirProducer myProducer;
	private int myIndex;
	
	public AirOutFlowRateActuatorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setAirOutputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(AirProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public BioModule getOutputModule(){
		return (BioModule)(myProducer);
	}
	
	public float getMax(){
		return myProducer.getAirOutputMaxFlowRate(myIndex);
	}
	
	public AirProducer getOutput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
}

package biosim.server.actuator.environment;

import biosim.idl.actuator.environment.AirInFlowRateActuatorOperations;
import biosim.idl.framework.AirConsumer;
import biosim.idl.framework.BioModule;
import biosim.server.actuator.framework.GenericActuatorImpl;

public class AirInFlowRateActuatorImpl extends GenericActuatorImpl implements AirInFlowRateActuatorOperations{
	private AirConsumer myConsumer;
	private int myIndex;
	
	public AirInFlowRateActuatorImpl(int pID, String pName){
		super(pID, pName);
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
	
	public BioModule getOutputModule(){
		return (BioModule)(myConsumer);
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	public float getMax(){
		return myConsumer.getAirInputMaxFlowRate(myIndex);
	}
}

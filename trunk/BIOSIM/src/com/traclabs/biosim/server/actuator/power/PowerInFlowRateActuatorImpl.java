package biosim.server.actuator.power;

import biosim.idl.actuator.power.PowerInFlowRateActuatorOperations;
import biosim.idl.framework.BioModule;
import biosim.idl.framework.PowerConsumer;
import biosim.server.actuator.framework.GenericActuatorImpl;

public class PowerInFlowRateActuatorImpl extends GenericActuatorImpl implements PowerInFlowRateActuatorOperations{
	private PowerConsumer myConsumer;
	private int myIndex;
	
	public PowerInFlowRateActuatorImpl(int pID, String pName){
		super(pID, pName);
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
	
	public BioModule getOutputModule(){
		return (BioModule)(myConsumer);
	}
	
	public PowerConsumer getOutput(){
		return myConsumer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	public float getMax(){
		return myConsumer.getPowerInputMaxFlowRate(myIndex);
	}
}

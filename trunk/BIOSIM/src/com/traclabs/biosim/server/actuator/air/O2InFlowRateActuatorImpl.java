package biosim.server.actuator.air;

import biosim.idl.actuator.air.O2InFlowRateActuatorOperations;
import biosim.idl.framework.BioModule;
import biosim.idl.framework.O2Consumer;
import biosim.server.actuator.framework.GenericActuatorImpl;

public class O2InFlowRateActuatorImpl extends GenericActuatorImpl implements O2InFlowRateActuatorOperations{
	private O2Consumer myConsumer;
	private int myIndex;
	
	public O2InFlowRateActuatorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setO2InputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(O2Consumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public BioModule getOutputModule(){
		return (BioModule)(myConsumer);
	}
	
	public O2Consumer getOutput(){
		return myConsumer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	public float getMax(){
		return myConsumer.getO2InputMaxFlowRate(myIndex);
	}
}

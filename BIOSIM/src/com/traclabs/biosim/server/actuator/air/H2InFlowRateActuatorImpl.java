package biosim.server.actuator.air;

import biosim.idl.actuator.air.H2InFlowRateActuatorOperations;
import biosim.idl.framework.BioModule;
import biosim.idl.framework.H2Consumer;
import biosim.server.actuator.framework.GenericActuatorImpl;

public class H2InFlowRateActuatorImpl extends GenericActuatorImpl implements H2InFlowRateActuatorOperations{
	private H2Consumer myConsumer;
	private int myIndex;
	
	public H2InFlowRateActuatorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setH2InputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(H2Consumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public BioModule getOutputModule(){
		return (BioModule)(myConsumer);
	}
	
	public H2Consumer getOutput(){
		return myConsumer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	public float getMax(){
		return myConsumer.getH2InputMaxFlowRate(myIndex);
	}
}

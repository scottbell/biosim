package biosim.server.actuator.waste;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.waste.*;
import biosim.idl.framework.*;

public class DryWasteInFlowRateActuatorImpl extends GenericActuatorImpl implements DryWasteInFlowRateActuatorOperations{
	private DryWasteConsumer myConsumer;
	private int myIndex;
	
	public DryWasteInFlowRateActuatorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setDryWasteInputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(DryWasteConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public BioModule getOutputModule(){
		return (BioModule)(myConsumer);
	}
	
	public DryWasteConsumer getOutput(){
		return myConsumer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	public float getMax(){
		return myConsumer.getDryWasteInputMaxFlowRate(myIndex);
	}
}

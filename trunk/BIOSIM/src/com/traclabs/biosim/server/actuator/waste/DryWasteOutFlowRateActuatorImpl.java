package biosim.server.actuator.waste;

import biosim.idl.actuator.waste.DryWasteOutFlowRateActuatorOperations;
import biosim.idl.framework.BioModule;
import biosim.idl.framework.DryWasteProducer;
import biosim.server.actuator.framework.GenericActuatorImpl;

public class DryWasteOutFlowRateActuatorImpl extends GenericActuatorImpl implements DryWasteOutFlowRateActuatorOperations{
	private DryWasteProducer myProducer;
	private int myIndex;
	
	public DryWasteOutFlowRateActuatorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setDryWasteOutputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(DryWasteProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public BioModule getOutputModule(){
		return (BioModule)(myProducer);
	}
	
	public DryWasteProducer getOutput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	public float getMax(){
		return myProducer.getDryWasteOutputMaxFlowRate(myIndex);
	}
}

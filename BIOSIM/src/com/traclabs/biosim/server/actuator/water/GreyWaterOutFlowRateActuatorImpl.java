package biosim.server.actuator.water;

import biosim.idl.actuator.water.GreyWaterOutFlowRateActuatorOperations;
import biosim.idl.framework.BioModule;
import biosim.idl.framework.GreyWaterProducer;
import biosim.server.actuator.framework.GenericActuatorImpl;

public class GreyWaterOutFlowRateActuatorImpl extends GenericActuatorImpl implements GreyWaterOutFlowRateActuatorOperations{
	private GreyWaterProducer myProducer;
	private int myIndex;
	
	public GreyWaterOutFlowRateActuatorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setGreyWaterOutputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(GreyWaterProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public BioModule getOutputModule(){
		return (BioModule)(myProducer);
	}
	
	public GreyWaterProducer getOutput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	public float getMax(){
		return myProducer.getGreyWaterOutputMaxFlowRate(myIndex);
	}
}

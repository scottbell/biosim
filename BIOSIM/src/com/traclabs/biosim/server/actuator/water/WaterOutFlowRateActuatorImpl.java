package biosim.server.actuator.water;

import biosim.idl.actuator.water.WaterOutFlowRateActuatorOperations;
import biosim.idl.framework.BioModule;
import biosim.idl.framework.WaterProducer;
import biosim.server.actuator.framework.GenericActuatorImpl;

public class WaterOutFlowRateActuatorImpl extends GenericActuatorImpl implements WaterOutFlowRateActuatorOperations{
	private WaterProducer myProducer;
	private int myIndex;
	
	public WaterOutFlowRateActuatorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setWaterOutputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(WaterProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public BioModule getOutputModule(){
		return (BioModule)(myProducer);
	}
	
	public WaterProducer getOutput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	public float getMax(){
		return myProducer.getWaterOutputMaxFlowRate(myIndex);
	}
}

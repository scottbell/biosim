package biosim.server.actuator.environment;

import biosim.idl.actuator.environment.WaterAirEnvironmentOutFlowRateActuatorOperations;
import biosim.idl.framework.BioModule;
import biosim.idl.framework.WaterAirProducer;
import biosim.server.actuator.framework.GenericActuatorImpl;

public class WaterAirEnvironmentOutFlowRateActuatorImpl extends GenericActuatorImpl implements WaterAirEnvironmentOutFlowRateActuatorOperations{
	private WaterAirProducer myProducer;
	private int myIndex;
	
	public WaterAirEnvironmentOutFlowRateActuatorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setWaterAirEnvironmentOutputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(WaterAirProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public BioModule getOutputModule(){
		return (BioModule)(myProducer);
	}
	
	public float getMax(){
		return myProducer.getWaterAirEnvironmentOutputMaxFlowRate(myIndex);
	}
	
	public WaterAirProducer getOutput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
}

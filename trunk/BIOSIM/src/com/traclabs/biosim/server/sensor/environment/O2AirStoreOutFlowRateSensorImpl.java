package biosim.server.sensor.environment;

import biosim.idl.framework.BioModule;
import biosim.idl.framework.O2AirProducer;
import biosim.idl.sensor.environment.O2AirStoreOutFlowRateSensorOperations;
import biosim.server.sensor.framework.GenericSensorImpl;

public class O2AirStoreOutFlowRateSensorImpl extends GenericSensorImpl implements O2AirStoreOutFlowRateSensorOperations{
	private O2AirProducer myProducer;
	private int myIndex;
	
	public O2AirStoreOutFlowRateSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getO2AirStoreOutputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(O2AirProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public float getMax(){
		return myProducer.getO2AirStoreOutputMaxFlowRate(myIndex);
	}
	
	public O2AirProducer getInput(){
		return myProducer;
	}
	
	public BioModule getInputModule(){
		return (BioModule)(myProducer);
	}
	
	public int getIndex(){
		return myIndex;
	}
}

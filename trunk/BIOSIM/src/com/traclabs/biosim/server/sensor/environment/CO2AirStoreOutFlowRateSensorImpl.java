package biosim.server.sensor.environment;

import biosim.idl.framework.BioModule;
import biosim.idl.framework.CO2AirProducer;
import biosim.idl.sensor.environment.CO2AirStoreOutFlowRateSensorOperations;
import biosim.server.sensor.framework.GenericSensorImpl;

public class CO2AirStoreOutFlowRateSensorImpl extends GenericSensorImpl implements CO2AirStoreOutFlowRateSensorOperations{
	private CO2AirProducer myProducer;
	private int myIndex;
	
	public CO2AirStoreOutFlowRateSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getCO2AirStoreOutputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(CO2AirProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public float getMax(){
		return myProducer.getCO2AirStoreOutputMaxFlowRate(myIndex);
	}
	
	public CO2AirProducer getInput(){
		return myProducer;
	}
	
	public BioModule getInputModule(){
		return (BioModule)(myProducer);
	}
	
	public int getIndex(){
		return myIndex;
	}
}

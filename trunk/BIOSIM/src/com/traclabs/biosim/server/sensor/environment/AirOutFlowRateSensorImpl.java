package biosim.server.sensor.environment;

import biosim.idl.framework.AirProducer;
import biosim.idl.framework.BioModule;
import biosim.idl.sensor.environment.AirOutFlowRateSensorOperations;
import biosim.server.sensor.framework.GenericSensorImpl;

public class AirOutFlowRateSensorImpl extends GenericSensorImpl implements AirOutFlowRateSensorOperations{
	private AirProducer myProducer;
	private int myIndex;
	
	public AirOutFlowRateSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getAirOutputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(AirProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public float getMax(){
		return myProducer.getAirOutputMaxFlowRate(myIndex);
	}
	
	public AirProducer getInput(){
		return myProducer;
	}
	
	public BioModule getInputModule(){
		return (BioModule)(myProducer);
	}
	
	public int getIndex(){
		return myIndex;
	}
	
}

package biosim.server.sensor.environment;

import biosim.idl.framework.BioModule;
import biosim.idl.framework.WaterAirProducer;
import biosim.idl.sensor.environment.WaterAirEnvironmentOutFlowRateSensorOperations;
import biosim.server.sensor.framework.GenericSensorImpl;

public class WaterAirEnvironmentOutFlowRateSensorImpl extends GenericSensorImpl implements WaterAirEnvironmentOutFlowRateSensorOperations{
	private WaterAirProducer myProducer;
	private int myIndex;
	
	public WaterAirEnvironmentOutFlowRateSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getWaterAirEnvironmentOutputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(WaterAirProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public float getMax(){
		return myProducer.getWaterAirEnvironmentOutputMaxFlowRate(myIndex);
	}
	
	public WaterAirProducer getInput(){
		return myProducer;
	}
	
	public BioModule getInputModule(){
		return (BioModule)(myProducer);
	}
	
	public int getIndex(){
		return myIndex;
	}
}

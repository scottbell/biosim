package biosim.server.sensor.environment;

import biosim.idl.framework.BioModule;
import biosim.idl.framework.WaterAirConsumer;
import biosim.idl.sensor.environment.WaterAirEnvironmentInFlowRateSensorOperations;
import biosim.server.sensor.framework.GenericSensorImpl;

public class WaterAirEnvironmentInFlowRateSensorImpl extends GenericSensorImpl implements WaterAirEnvironmentInFlowRateSensorOperations{
	private WaterAirConsumer myConsumer;
	private int myIndex;
	
	public WaterAirEnvironmentInFlowRateSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getWaterAirEnvironmentInputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(WaterAirConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public float getMax(){
		return myConsumer.getWaterAirEnvironmentInputMaxFlowRate(myIndex);
	}
	
	public WaterAirConsumer getInput(){
		return myConsumer;
	}
	
	public BioModule getInputModule(){
		return (BioModule)(myConsumer);
	}
	
	public int getIndex(){
		return myIndex;
	}
}

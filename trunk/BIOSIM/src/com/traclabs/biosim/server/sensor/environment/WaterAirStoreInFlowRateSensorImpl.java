package biosim.server.sensor.environment;

import biosim.idl.framework.BioModule;
import biosim.idl.framework.WaterAirConsumer;
import biosim.idl.sensor.environment.WaterAirStoreInFlowRateSensorOperations;
import biosim.server.sensor.framework.GenericSensorImpl;

public class WaterAirStoreInFlowRateSensorImpl extends GenericSensorImpl implements WaterAirStoreInFlowRateSensorOperations{
	private WaterAirConsumer myConsumer;
	private int myIndex;
	
	public WaterAirStoreInFlowRateSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getWaterAirStoreInputActualFlowRate(myIndex);
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

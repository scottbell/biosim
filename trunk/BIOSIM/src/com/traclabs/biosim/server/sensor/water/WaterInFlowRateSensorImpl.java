package biosim.server.sensor.water;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.water.*;
import biosim.idl.framework.*;

public class WaterInFlowRateSensorImpl extends GenericSensorImpl implements WaterInFlowRateSensorOperations{
	private WaterConsumer myConsumer;
	private int myIndex;
	
	public WaterInFlowRateSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getWaterInputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(WaterConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public WaterConsumer getInput(){
		return myConsumer;
	}
	
	public float getMax(){
		return myConsumer.getWaterInputMaxFlowRate(myIndex);
	}
	
	public BioModule getInputModule(){
		return (BioModule)(myConsumer);
	}
	
	public int getIndex(){
		return myIndex;
	}
}

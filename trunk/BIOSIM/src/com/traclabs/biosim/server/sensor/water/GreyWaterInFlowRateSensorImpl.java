package biosim.server.sensor.water;

import biosim.idl.framework.BioModule;
import biosim.idl.framework.GreyWaterConsumer;
import biosim.idl.sensor.water.GreyWaterInFlowRateSensorOperations;
import biosim.server.sensor.framework.GenericSensorImpl;

public class GreyWaterInFlowRateSensorImpl extends GenericSensorImpl implements GreyWaterInFlowRateSensorOperations{
	private GreyWaterConsumer myConsumer;
	private int myIndex;
	
	public GreyWaterInFlowRateSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getGreyWaterInputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(GreyWaterConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public GreyWaterConsumer getInput(){
		return myConsumer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	public BioModule getInputModule(){
		return (BioModule)(myConsumer);
	}
	
	public float getMax(){
		return myConsumer.getGreyWaterInputMaxFlowRate(myIndex);
	}
}

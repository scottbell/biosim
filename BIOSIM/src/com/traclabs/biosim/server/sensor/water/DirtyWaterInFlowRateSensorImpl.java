package biosim.server.sensor.water;

import biosim.idl.framework.BioModule;
import biosim.idl.framework.DirtyWaterConsumer;
import biosim.idl.sensor.water.DirtyWaterInFlowRateSensorOperations;
import biosim.server.sensor.framework.GenericSensorImpl;

public class DirtyWaterInFlowRateSensorImpl extends GenericSensorImpl implements DirtyWaterInFlowRateSensorOperations{
	private DirtyWaterConsumer myConsumer;
	private int myIndex;
	
	public DirtyWaterInFlowRateSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getDirtyWaterInputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(DirtyWaterConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public DirtyWaterConsumer getInput(){
		return myConsumer;
	}
	
	public float getMax(){
		return myConsumer.getDirtyWaterInputMaxFlowRate(myIndex);
	}
	
	public BioModule getInputModule(){
		return (BioModule)(myConsumer);
	}
	
	public int getIndex(){
		return myIndex;
	}
}

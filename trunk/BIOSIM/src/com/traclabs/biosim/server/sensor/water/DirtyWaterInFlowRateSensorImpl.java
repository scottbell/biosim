package biosim.server.sensor.water;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.water.*;
import biosim.idl.framework.*;

public class DirtyWaterInFlowRateSensorImpl extends GenericSensorImpl implements DirtyWaterInFlowRateSensorOperations{
	private DirtyWaterConsumer myConsumer;
	private int myIndex;
	
	public DirtyWaterInFlowRateSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		double preFilteredValue = getInput().getDirtyWaterInputActualFlowRate(myIndex);
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
	
	public int getIndex(){
		return myIndex;
	}
	
	/**
	* Returns the name of this module (DirtyWaterInFlowRateSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "DirtyWaterInFlowRateSensor"+getID();
	}
}

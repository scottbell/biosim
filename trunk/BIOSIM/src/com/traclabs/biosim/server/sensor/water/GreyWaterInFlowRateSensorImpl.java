package biosim.server.sensor.water;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.water.*;
import biosim.idl.framework.*;

public class GreyWaterInFlowRateSensorImpl extends GenericSensorImpl implements GreyWaterInFlowRateSensorOperations{
	private GreyWaterConsumer myConsumer;
	private int myIndex;
	
	public GreyWaterInFlowRateSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		double preFilteredValue = getInput().getGreyWaterInputActualFlowRate(myIndex);
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
	
	/**
	* Returns the name of this module (GreyWaterInFlowRateSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "GreyWaterInFlowRateSensor"+getID();
	}
}

package biosim.server.sensor.water;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.water.*;
import biosim.idl.framework.*;

public class PotableWaterOutFlowRateSensorImpl extends GenericSensorImpl implements PotableWaterOutFlowRateSensorOperations{
	private PotableWaterProducer myProducer;
	private int myIndex;
	
	public PotableWaterOutFlowRateSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getPotableWaterOutputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(PotableWaterProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public PotableWaterProducer getInput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	public float getMax(){
		return myProducer.getPotableWaterOutputMaxFlowRate(myIndex);
	}
	
	/**
	* Returns the name of this module (PotableWaterOutFlowRateSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "PotableWaterOutFlowRateSensor"+getID();
	}
}

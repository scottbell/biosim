package biosim.server.sensor.water;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.water.*;
import biosim.idl.framework.*;

public class GreyWaterOutFlowRateSensorImpl extends GenericSensorImpl implements GreyWaterOutFlowRateSensorOperations{
	private GreyWaterProducer myProducer;
	private int myIndex;
	
	public GreyWaterOutFlowRateSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getGreyWaterOutputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(GreyWaterProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public GreyWaterProducer getInput(){
		return myProducer;
	}
	
	public float getMax(){
		return myProducer.getGreyWaterOutputMaxFlowRate(myIndex);
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	protected BioModule getInputModule(){
		return (BioModule)(myProducer);
	}
	
	/**
	* Returns the name of this module (GreyWaterOutFlowRateSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "GreyWaterOutFlowRateSensor"+getID();
	}
}
